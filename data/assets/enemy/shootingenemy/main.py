import os
from PIL import Image, ImageOps

# Create required directories
os.makedirs("left", exist_ok=True)
os.makedirs("right", exist_ok=True)

# Define file mappings and sprite information
file_info = {
    "death.png": {"prefix": "death", "frames": 10},
    "attack.png": {"prefix": "attack", "frames": 6}, 
    "jump.png": {"prefix": "jump", "frames": 2},
    "damage.png": {"prefix": "damage", "frames": 3},
    "idle.png": {"prefix": "neutral", "frames": 10}
}

# Process each sprite sheet
for source_file, info in file_info.items():
    try:
        # Open the source image
        img = Image.open(source_file)
        img_width, img_height = img.size
        
        # Get sprite information
        prefix = info["prefix"]
        num_frames = info["frames"]
        
        # Calculate frame width based on number of frames
        frame_width = img_width // num_frames
        
        # Extract each sprite
        for i in range(num_frames):
            # Calculate coordinates for cropping
            left = i * frame_width
            right = left + frame_width
            
            # Crop the sprite
            sprite = img.crop((left, 0, right, img_height))
            
            # Create mirrored version
            mirrored_sprite = ImageOps.mirror(sprite)
            
            # Save original to 'left' folder
            sprite.save(os.path.join("left", f"{prefix}{i}.png"))
            
            # Save mirrored to 'right' folder
            mirrored_sprite.save(os.path.join("right", f"{prefix}{i}.png"))
        
        print(f"Processed {source_file}: {num_frames} sprites extracted")
    
    except Exception as e:
        print(f"Error processing {source_file}: {str(e)}")

print("Sprite extraction complete!")