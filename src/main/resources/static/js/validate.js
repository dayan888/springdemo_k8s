
window.onload = function() {
    document.getElementById("loginForm").onsubmit = function() {
        if (!document.getElementsByName("username")[0].value || !document.getElementsByName("password")[0].value) {
            alert("Enter ID and PW");
            return false;
        }
    }
}
