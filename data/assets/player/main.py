from PIL import Image
import os

# Input folder containing .png files
input_folder = "."
output_folder = "left"
os.makedirs(output_folder, exist_ok=True)

# Process all .png files in the input folder
for file_name in os.listdir(input_folder):
    if file_name.endswith(".png"):
        # Open the image file
        file_path = os.path.join(input_folder, file_name)
        image = Image.open(file_path)

        # Mirror the image horizontally
        mirrored_image = image.transpose(Image.FLIP_LEFT_RIGHT)

        # Save the mirrored image in the 'left' subfolder with the same name
        output_path = os.path.join(output_folder, file_name)
        mirrored_image.save(output_path)

print(f"Mirrored images have been saved to the '{output_folder}' folder.")

