from PIL import Image
import os

# Define folders for left (original) and right (mirrored) sprites
left_folder = "./left"
right_folder = "./right"
os.makedirs(left_folder, exist_ok=True)
os.makedirs(right_folder, exist_ok=True)

# Function to mirror an image horizontally
def mirror_image(img):
    return img.transpose(Image.FLIP_LEFT_RIGHT)

# Define the specific image file to process
filename = "bullet.png"
current_dir = os.path.dirname(os.path.abspath(__file__))
image_path = os.path.join(current_dir, filename)

# Check if the target image file exists
if os.path.isfile(image_path):
    try:
        # Load the image
        img = Image.open(image_path)

        # Save the original image to the left folder
        left_file_path = os.path.join(left_folder, filename)
        img.save(left_file_path)
        print(f"Saved original {filename} to '{left_folder}'")

        # Create a mirrored version and save it to the right folder
        mirrored_img = mirror_image(img)
        right_file_path = os.path.join(right_folder, filename)
        mirrored_img.save(right_file_path)
        print(f"Saved mirrored {filename} to '{right_folder}'")

    except Exception as e:
        print(f"Error processing {filename}: {e}")
else:
    print(f"Error: File '{filename}' not found in the directory '{current_dir}'.")

print("Processing complete.")