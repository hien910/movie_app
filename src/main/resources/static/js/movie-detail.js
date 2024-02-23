const stars = document.querySelectorAll(".rating-container .star");
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

// Format date
const formatDate = (dateString) => {
    const date = new Date(dateString);

    const day = `0${date.getDate()}`.slice(-2); // `05` -> 05 , '015' -> 15
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
};

// Lấy ratingText từ rating
const getRatingText = (rating) => {
    switch (rating) {
        case 1:
            return "Tệ";
        case 2:
            return "Kém";
        case 3:
            return "Trung bình";
        case 4:
            return "Tạm được";
        case 5:
            return "Hay";
        case 6:
            return "Rất hay";
        case 7:
            return "Tuyệt vời";
        case 8:
            return "Tuyệt hảo";
        case 9:
            return "Xuất sắc";
        case 10:
            return "Quá tuyệt vời";
        default:
            return "Chưa có đánh giá";
    }
}

// Hiển thị danh sách đánh giá lên giao diện
const reviewListEl = document.querySelector(".review-list");
const renderReivew = (reviews) => {
    let html = ""
    reviews.forEach(review => {
        html += `
            <div class="rating-item d-flex align-items-center mb-3 pb-3">
                <div class="rating-avatar align-self-start">
                    <img src="${review.user.avatar}" alt="${review.user.name}">
                </div>
                <div class="rating-info ms-3">
                    <div class="d-flex align-items-center">
                        <p class="rating-name mb-0">
                            <strong>${review.user.name}</strong>
                        </p>
                        <p class="rating-time mb-0 ms-2">${formatDate(review.createdAt)}</p>
                    </div>
                    <div class="rating-star">
                        <p class="mb-0 fw-bold">
                            <span class="rating-icon"><i class="fa fa-star" ></i></span>
                            <span>${review.rating}/10 ${getRatingText(review.rating)}</span>
                        </p>
                    </div>
                    <p class="rating-content mt-1 mb-0 text-muted">${review.comment}</p>
                    <div>
                        <button class="btn bg-transparent text-primary p-0 me-1 text-decoration-underline" onclick="openModalUpdateReview(${review.id})">Sửa</button>
                        <button class="btn bg-transparent text-danger p-0 text-decoration-underline" onclick="deleteReview(${review.id})">Xóa</button>
                    </div>
                </div>
            </div>
        `
    });

    reviewListEl.innerHTML = html;
}

const renderPagination = (reviews) => {
    $('#review-pagination').pagination({
        dataSource: reviews,
        pageSize: 5,
        callback: function (data, pagination) {
            renderReivew(data);
        }
    })
}

// Xử lý mở modal đánh giá
let idReviewUpdate = null;
const modalReviewConfig = new bootstrap.Modal('#modalReview', {
    keyboard: false
})
const modalReviewTitleEl = document.getElementById("modal-review-title");
const reviewContentEl = document.getElementById("review-content");
const btnOpenModalReview = document.getElementById("btn-open-modal-review");

// Xử lý khi mở modal tạo mới review
btnOpenModalReview.addEventListener("click", () => {
    modalReviewConfig.show();
    modalReviewTitleEl.textContent = `Viết đánh giá`;
});

// Xử lý khi mở modal cập nhật review
const openModalUpdateReview = reviewId => {
    modalReviewConfig.show();
    modalReviewTitleEl.textContent = `Cập nhật đánh giá`;

    // Tìm kiếm review theo id
    const review = reviewList.find(review => review.id === reviewId);

    // Cập nhật dữ liệu cho modal
    reviewContentEl.value = review.comment;

    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
    highlightStars(currentRating);

    // Lưu lại id review cần cập nhật
    idReviewUpdate = reviewId;
}

// Xử lý khi đóng modal
const modalReviewEl = document.getElementById('modalReview')
modalReviewEl.addEventListener('hidden.bs.modal', event => {
    // reset modal
    currentRating = 0;
    ratingValue.textContent = "";
    resetStars();
    reviewContentEl.value = "";

    // reset id review cần cập nhật
    idReviewUpdate = null;
})

// Xử lý khi click vào nút lưu đánh giá
const btnHandle = document.getElementById("btn-handle");
btnHandle.addEventListener("click", (e) => {
    e.preventDefault();

    // Nếu form invalid thì return
    if (!$("#form-review").valid()) return;

    if (idReviewUpdate) {
        updateReview();
    } else {
        createReview();
    }
})

// Validate form
$('#form-review').validate({
    rules: {
        content: {
            required: true
        }
    },
    messages: {
        content: {
            required: "Vui lòng nhập nội dung đánh giá"
        }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

// Tạo mới review
const createReview = () => {
    if (currentRating === 0) {
        toastr.warning("Vui lòng chọn số sao");
        return;
    }

    const data = {
        comment: reviewContentEl.value,
        rating: currentRating,
        movieId: movie.id
    }

    // Gọi API để tạo mới review
    axios.post("/api/reviews", data)
        .then((res) => {
            reviewList.unshift(res.data);
            renderPagination(reviewList);

            modalReviewConfig.hide();
            toastr.success("Tạo đánh giá thành công");
        })
        .catch((err) => {
            console.log(err);
            toastr.error(err.response.data.message)
        });
}

// Cập nhật review
const updateReview = () => {
    if (currentRating === 0) {
        toastr.warning("Vui lòng chọn số sao");
        return;
    }

    const data = {
        comment: reviewContentEl.value,
        rating: currentRating,
        movieId: movie.id
    }
    console.log(data);

    // Gọi API để cập nhật review
    axios.put(`/api/reviews/${idReviewUpdate}`, data)
        .then((res) => {
            const reviewIndex = reviewList.findIndex(review => review.id === idReviewUpdate);
            reviewList[reviewIndex] = res.data;
            renderPagination(reviewList);

            modalReviewConfig.hide();
            toastr.success("Cập nhật đánh giá thành công");
        })
        .catch((err) => {
            console.log(err);
            toastr.error(err.response.data.message)
        });
}

// Xóa review
const deleteReview = id => {
    const isConfrim = confirm("Bạn có chắc chắn muốn xóa đánh giá này?");
    if (!isConfrim) return;

    // Gọi API để xóa review
    axios.delete(`/api/reviews/${id}`)
        .then((res) => {
            reviewList = reviewList.filter(review => review.id !== id);
            renderPagination(reviewList);
            toastr.success("Xóa đánh giá thành công");
        })
        .catch((err) => {
            console.log(err);
            toastr.error(err.response.data.message)
        });
}

// Vừa vào trang thì render danh sách đánh giá + phân trang
renderPagination(reviewList);

