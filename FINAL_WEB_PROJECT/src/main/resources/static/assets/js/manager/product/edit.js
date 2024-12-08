document.addEventListener("DOMContentLoaded", () => {
    const imageList = document.getElementById("imageList");
    const imageUrlInput = document.getElementById("imageUrl");
    const addImageBtn = document.getElementById("addImageBtn");

    const addImage = (url) => {
        const div = document.createElement("div");
        div.className = "image-preview";
        div.innerHTML = `
            <img src="${url}" alt="Image preview">
            <button class="remove-btn">&times;</button>
        `;
        div.querySelector(".remove-btn").addEventListener("click", () => {
            div.remove();
        });
        imageList.appendChild(div);
    };

    addImageBtn.addEventListener("click", () => {
        const url = imageUrlInput.value.trim();
        if (url) {
            addImage(url);
            imageUrlInput.value = "";
        }
    });

    // Populate existing images (simulating a preloaded state)
    const existingImages = ["https://via.placeholder.com/100", "https://via.placeholder.com/150"];
    existingImages.forEach(addImage);
});
