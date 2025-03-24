from PIL import Image
import numpy as np
import os
import cv2

def extract_sprites(image_path, output_dir, scale_factor=2):
    # Create output directory if it doesn't exist
    os.makedirs(output_dir, exist_ok=True)
    
    # Load the image
    img = cv2.imread(image_path, cv2.IMREAD_UNCHANGED)
    
    # If the image has an alpha channel, use it for detection
    if img.shape[2] == 4:
        # Use alpha channel to create a binary mask
        gray = img[:, :, 3]
    else:
        # Convert to grayscale
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        # Threshold to create binary image
        _, gray = cv2.threshold(gray, 250, 255, cv2.THRESH_BINARY_INV)
    
    # Find connected components
    num_labels, labels, stats, centroids = cv2.connectedComponentsWithStats(gray, connectivity=8)
    
    # Skip the first component (background)
    sprites = []
    for i in range(1, num_labels):
        x, y, w, h = stats[i, cv2.CC_STAT_LEFT], stats[i, cv2.CC_STAT_TOP], \
                    stats[i, cv2.CC_STAT_WIDTH], stats[i, cv2.CC_STAT_HEIGHT]
        
        # Skip very small components (likely noise)
        if w < 3 or h < 3:
            continue
            
        sprites.append({
            'id': i,
            'x': x,
            'y': y,
            'w': w,
            'h': h,
            'center_y': y + h//2
        })
    
    # Sort sprites by vertical position
    sprites.sort(key=lambda s: s['y'])
    
    # Group sprites into rows
    rows = []
    if sprites:
        current_row = [sprites[0]]
        row_height = sprites[0]['h']
        
        for sprite in sprites[1:]:
            if abs(sprite['center_y'] - current_row[0]['center_y']) < max(row_height, sprite['h']):
                current_row.append(sprite)
                row_height = max(row_height, sprite['h'])
            else:
                rows.append(sorted(current_row, key=lambda s: s['x']))
                current_row = [sprite]
                row_height = sprite['h']
        
        if current_row:
            rows.append(sorted(current_row, key=lambda s: s['x']))
    
    # Use PIL for saving with NEAREST resampling
    pil_img = Image.open(image_path).convert("RGBA")
    
    # Extract and save each sprite
    for row_idx, row in enumerate(rows):
        for col_idx, sprite in enumerate(row):
            x, y, w, h = sprite['x'], sprite['y'], sprite['w'], sprite['h']
            
            # Crop the sprite
            sprite_img = pil_img.crop((x, y, x + w, y + h))
            
            # Calculate the size for a square image that fits the sprite
            max_dim = max(w, h)
            
            # Create a new image with transparent background
            square_img = Image.new('RGBA', (max_dim, max_dim), (0, 0, 0, 0))
            
            # Paste the sprite in the center of the square
            paste_x = (max_dim - w) // 2
            paste_y = (max_dim - h) // 2
            square_img.paste(sprite_img, (paste_x, paste_y), sprite_img)
            
            # Scale up the image to preserve pixel art quality
            scaled_size = (max_dim * scale_factor, max_dim * scale_factor)
            scaled_img = square_img.resize(scaled_size, resample=Image.NEAREST)
            
            # Save with the tile naming convention
            filename = f"tile{row_idx:02d}{col_idx:02d}.png"
            scaled_img.save(
                os.path.join(output_dir, filename),
                optimize=False,
                quality=100
            )
            print(f"Saved {filename} - Position: ({x}, {y}), Size: {scaled_size[0]}x{scaled_size[1]}")

if __name__ == "__main__":
    extract_sprites("GUI.png", "output_sprites", scale_factor=3)
