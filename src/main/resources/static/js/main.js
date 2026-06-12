// Campus Food Review - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts after 5 seconds
    setTimeout(function() {
        document.querySelectorAll('.alert-dismissible').forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
});

// Star rating select
function selectRating(radio) {
    var group = radio.closest('.btn-group');
    group.querySelectorAll('.btn').forEach(function(btn) {
        btn.classList.remove('active');
    });
    if (radio.checked) {
        var label = group.querySelector('label[for="' + radio.id + '"]');
        if (label) label.classList.add('active');
    }
}

// Toggle Like via AJAX
function toggleLike(button, reviewId) {
    button.disabled = true;
    var csrfToken = getCsrfToken();

    fetch('/review/' + reviewId + '/like', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.success) {
            var countSpan = button.querySelector('span');
            var currentCount = parseInt(countSpan.textContent);
            if (data.liked) {
                button.classList.remove('btn-outline-secondary');
                button.classList.add('btn-outline-danger');
                countSpan.textContent = currentCount + 1;
            } else {
                button.classList.remove('btn-outline-danger');
                button.classList.add('btn-outline-secondary');
                countSpan.textContent = currentCount - 1;
            }
        } else {
            if (data.message) alert(data.message);
        }
        button.disabled = false;
    })
    .catch(function() {
        alert('操作失败，请重试');
        button.disabled = false;
    });
}

// Toggle Favorite via AJAX
function toggleFavorite(button, shopId) {
    button.disabled = true;
    var csrfToken = getCsrfToken();

    fetch('/favorite/' + shopId + '/toggle', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.success) {
            var icon = button.querySelector('i');
            var countSpan = button.querySelector('span');
            if (data.favorited) {
                button.classList.remove('btn-outline-danger');
                button.classList.add('btn-danger');
                icon.classList.remove('bi-heart');
                icon.classList.add('bi-heart-fill');
            } else {
                button.classList.remove('btn-danger');
                button.classList.add('btn-outline-danger');
                icon.classList.remove('bi-heart-fill');
                icon.classList.add('bi-heart');
            }
            countSpan.textContent = data.count;
        } else {
            if (data.message) alert(data.message);
        }
        button.disabled = false;
    })
    .catch(function() {
        alert('操作失败，请重试');
        button.disabled = false;
    });
}

// Delete Review
function deleteReview(reviewId) {
    if (!confirm('确定要删除这条点评吗？')) return;
    var csrfToken = getCsrfToken();

    fetch('/review/' + reviewId + '/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(function() {
        window.location.reload();
    })
    .catch(function() {
        alert('删除失败');
    });
}

// Get CSRF token from meta tag or cookie
function getCsrfToken() {
    var meta = document.querySelector('meta[name="_csrf"]');
    if (meta) return meta.getAttribute('content');

    // Try to get from cookie
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith('XSRF-TOKEN=')) {
            return cookie.substring('XSRF-TOKEN='.length);
        }
    }
    return '';
}

// Image preview on file select
document.addEventListener('change', function(e) {
    if (e.target && e.target.type === 'file' && e.target.accept === 'image/*') {
        var file = e.target.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function(ev) {
                var preview = e.target.parentElement.querySelector('.preview-image');
                if (!preview) {
                    preview = document.createElement('img');
                    preview.className = 'preview-image img-thumbnail mt-2';
                    preview.style.maxHeight = '150px';
                    e.target.parentElement.appendChild(preview);
                }
                preview.src = ev.target.result;
            };
            reader.readAsDataURL(file);
        }
    }
});
