const stars = document.querySelectorAll(".star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

// Function to open the modal
function openModal() {
    document.getElementById('reviewModal').style.display = 'block';
}

// Function to close the modal
function closeModal() {
    document.getElementById('reviewModal').style.display = 'none';
}

// Function to submit the review (you can customize this function based on your requirements)
function submitReview() {
    const reviewText = document.getElementById('reviewTextarea').value;



    // Example POST method implementation:
    async function postData(url = "http://localhost:8000/api/reviews", data = {}) {
        // Default options are marked with *
        const response = await fetch(url, {
            method: "POST", // *GET, POST, PUT, DELETE, etc.

            headers: {
                "Content-Type": "application/json",

            },

            body: JSON.stringify(data), // body data type must match "Content-Type" header
        });
        return response.json(); // parses JSON response into native JavaScript objects
    }

    postData("http://localhost:8000/api/reviews", {rating: currentRating*2, comment: reviewText, movieId: 3}).then((data) => {
        console.log(data);
    });
    closeModal();
}

// Event listener for the button
document.getElementById('openModalBtn').addEventListener('click', openModal);

