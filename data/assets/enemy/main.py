from PIL import Image
import os

# Load the sprite sheet
sprite_sheet_path = "enemy.png"
sprite_sheet = Image.open(sprite_sheet_path)

# Dimensions of each sprite (adjust these values if needed)
sprite_width = 32  # Width of a single sprite
sprite_height = 32  # Height of a single sprite

# Number of rows and columns in the sprite sheet
sheet_width, sheet_height = sprite_sheet.size
columns = sheet_width // sprite_width  # Number of sprites per row
rows = sheet_height // sprite_height   # Total rows in the sprite sheet

# Animation names corresponding to each row (row indices are zero-based)
animations = [
    "idle",    # Row 1
    "run",     # Row 2
    "stop",    # Row 3
    "jump",    # Row 4
    "fall",    # Row 5
    "death",   # Row 6
    "damage",  # Row 7
    "dash"     # Row 8
]

# Output folders for right (original) and left (mirrored) sprites
right_folder = "./right"
left_folder = "./left"
os.makedirs(right_folder, exist_ok=True)
os.makedirs(left_folder, exist_ok=True)

# Function to mirror a sprite horizontally
def mirror_sprite(sprite):
    return sprite.transpose(Image.FLIP_LEFT_RIGHT)

# Function to check if a sprite is empty (fully transparent)
def is_empty_sprite(sprite):
    bbox = sprite.getbbox()
    return bbox is None

# Process each row and column in the sprite sheet
for row_index, animation_name in enumerate(animations):
    frame_index = 0  # To track non-empty frames for each animation type
    for col in range(columns):
        # Calculate the bounding box for the current sprite
        x1 = col * sprite_width
        y1 = row_index * sprite_height
        x2 = x1 + sprite_width
        y2 = y1 + sprite_height

        # Crop the sprite from the sheet
        sprite = sprite_sheet.crop((x1, y1, x2, y2))

        # Skip empty sprites
        if is_empty_sprite(sprite):
            continue

        # Generate filenames for right and left versions of the sprite
        base_filename = f"{animation_name}{frame_index}.png"
        right_file_path = os.path.join(right_folder, base_filename)
        left_file_path = os.path.join(left_folder, base_filename)

        # Save the original (right) sprite
        sprite.save(right_file_path)

        # Mirror the sprite and save it as the left version
        mirrored_sprite = mirror_sprite(sprite)
        mirrored_sprite.save(left_file_path)

        frame_index += 1  # Increment frame index for non-empty sprites

print(f"Original sprites have been saved to '{right_folder}', and mirrored sprites have been saved to '{left_folder}'.")

