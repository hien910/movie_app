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
    const movieId = document.getElementById("movieId").getAttribute("data-movie-id");

    postData("http://localhost:8000/api/reviews", {rating: currentRating*2, comment: reviewText, movieId: movieId }).then((data) => {
        console.log(data);
    });
    closeModal();
}

// Event listener for the button
document.getElementById('openModalBtn').addEventListener('click', openModal);


// Lắng nghe sự kiện click trên nút "Xóa"
// document.querySelectorAll('.deleteModalBtn').forEach(function(button) {
//     button.addEventListener('click', function () {
//         // Lấy ID đánh giá từ phần tử div chứa cùng cấp
//         const reviewId = this.closest('.review-container').querySelector('.reviewId').dataset.reviewId;
//         console.log(reviewId);
//
//         // Kiểm tra xem ID có tồn tại không
//         if (reviewId) {
//             // Gọi hàm xóa đánh giá với ID đã thu được
//             deleteReview(reviewId);
//         } else {
//             console.error('Không có ID đánh giá để xóa.');
//         }
//     });
// });

// Hàm xóa đánh giá
// function deleteReview(reviewId) {
//
//     // Gửi yêu cầu DELETE đến API
//     fetch(`http://localhost:8000/api/reviews/${reviewId}`, {
//         method: 'DELETE',
//         headers: {
//             'Content-Type': 'application/json',
//             // Nếu cần, bạn có thể thêm các header khác ở đây
//         },
//         // body: JSON.stringify(data), // Nếu bạn cần gửi dữ liệu cùng với yêu cầu
//     })
//         .then(response => {
//             // Kiểm tra xem yêu cầu đã thành công không
//             if (!response.ok) {
//                 throw new Error(`Xóa đánh giá không thành công. Mã lỗi: ${response.status}`);
//             }
//             // Trả về promise giải quyết với response json
//             return response.json();
//         })
//         .then(data => {
//             // Xử lý dữ liệu trả về nếu cần thiết
//             console.log('Đánh giá đã được xóa thành công:', data);
//             // Sau khi xóa thành công, bạn có thể thực hiện các bước khác nếu cần
//         })
//         .catch(error => {
//             console.error('Lỗi khi xóa đánh giá:', error);
//             // Xử lý lỗi nếu cần thiết
//         });
//     console.log('Đánh giá đã được xóa với ID: ' + reviewId);
// }

