<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link
        href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/index.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/model.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>

<body>

    <!-- Header Section -->
   <header class="header">
       <div class="logo">ABC Modern Kitchen</div>
       <nav class="nav">
           <ul>
               <li><a href="#home">Home</a></li>
               <li><a href="#about">About Us</a></li>
               <li><a href="#gallery">Gallery</a></li>
               <li><a href="#services">Services & Offers</a></li>
               <li><a href="#feedback">Customer Feedback</a></li>
           </ul>
       </nav>

       <div class="auth-buttons">
           <button class="btn sign-in"
               onclick="openModal('loginModal', '<%= request.getContextPath() %>/RegisterServlet?action=login')">Sign
               In</button>
           <button class="btn sign-up"
               onclick="openModal('registerModal', '<%= request.getContextPath() %>/RegisterServlet?action=customerRegister')">Sign
               Up</button>
       </div>


   </header>

   <!-- Login Modal -->
   <div id="loginModal" class="modal">
       <div class="modal-content">
           <span class="close" onclick="closeModal('loginModal')">&times;</span>
           <div id="loginContent"></div>
       </div>
   </div>

   <!-- Register Modal -->
   <div id="registerModal" class="modal">
       <div class="modal-content">
           <span class="close" onclick="closeModal('registerModal')">&times;</span>
           <div id="registerContent"></div>
       </div>
   </div>


   <!-- Home Section -->
   <section id="home" class="home">
       <div class="home-content">
           <div class="home-text">
               <h1>Welcome to<br>ABC Modern Kitchen</h1>
               <p>Experience the finest dining with our modern culinary creations.
                   Explore our menu, book a table, or learn about us!</p>
           </div>
           <div class="home-image">
               <img src="assets/Image_4.png" alt="Food Image">
           </div>
       </div>
   </section>

   <!-- About Section -->
   <section id="about" class="about">
       <h2>About Us</h2>
       <div class="about-content">
           <div class="about-text">
               <p>Founded in 2023, ABC Modern Kitchen blends tradition with innovation to serve delicious
                   meals.
                   Our passion is about quality and customer satisfaction.</p>
               <p><b>Locations:</b> 5 across Sri Lanka<br>
                   <b>Mission:</b> To delight every palate
               </p>
           </div>
           <div class="about-image">
               <img src="assets/Image_9.png" alt="Our Team">
           </div>
       </div>
   </section>

   <!-- Gallery Section -->
   <section id="gallery" class="gallery">
       <h2>Gallery</h2>
       <div class="gallery-grid">
           <%-- Example JSP loop placeholder --%>
               <% String[]
                   images={"Image_1.png","Image_2.png","Image_3.png", "Image_6.png","Image_7.png","Image_8.png"};
                   for (String img : images) { %>
                   <div class="gallery-item">
                       <img src="assets/<%= img %>" alt="Gallery Image">
                   </div>
                   <% } %>
       </div>
   </section>

   <!-- Services & Offers -->
   <section id="services" class="services">
       <h2>Services & Offers</h2>
       <div class="service-container">
           <div class="service-box">
               <img src="assets/Image_13.png" alt="Catering">
               <h3>Catering</h3>
               <p>Special packages for events starting at $100.</p>
           </div>
           <div class="service-box">
               <img src="assets/Image_12.png" alt="Dine-In">
               <h3>Dine-In</h3>
               <p>Enjoy our cozy ambiance with a 10% discount this month.</p>
           </div>
           <div class="service-box">
               <img src="assets/Image_11.png" alt="Takeaway">
               <h3>Takeaway</h3>
               <p>Free delivery on orders over $20.</p>
           </div>
       </div>
   </section>

   <!-- Customer Feedback -->
   <section id="feedback" class="feedback">
       <h2>Customer Feedback</h2>
       <div class="feedback-container">
           <% String[][] feedback={
               {"Customer_1.jpg", "Lorem ipsum dolor sit amet, consectetur adipiscing elit." },
               {"Customer_2.jpg", "Praesent volutpat nisl quis eros sollicitudin varius." },
               {"Customer_3.jpg", "Suspendisse potenti. Donec sit amet sapien at felis aliquam egestas." }
               }; for (String[] f : feedback) { %>
               <div class="feedback-card">
                   <img src="assets/<%= f[0] %>" alt="Customer">
                   <p>
                       <%= f[1] %>
                   </p>
               </div>
               <% } %>
       </div>
   </section>

   <!-- Footer -->
   <footer class="footer">
       <div class="footer-content">
           <h3>ABC Modern Kitchen</h3>
           <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
               Nulla molestie nisl at libero condimentum, eget suscipit tellus sagittis.</p>
           <div class="social-icons">
               <a href="#"><i class="fab fa-facebook"></i></a>
               <a href="#"><i class="fab fa-instagram"></i></a>
               <a href="#"><i class="fab fa-twitter"></i></a>
               <a href="#"><i class="fab fa-youtube"></i></a>
           </div>
           <div class="contact-info">
               <p><i class="fas fa-envelope"></i> abckitchen@gmail.com</p>
               <p><i class="fas fa-phone"></i> +94 123456789</p>
               <p><i class="fas fa-map-marker"></i> No.123, Main Street, Colombo, Sri Lanka</p>
           </div>
       </div>
   </footer>

   <script>
       // Smooth scrolling for navigation links
       document.querySelectorAll('.nav a').forEach(anchor => {
           anchor.addEventListener('click', function (e) {
               e.preventDefault();
               const targetId = this.getAttribute('href');
               const targetElement = document.querySelector(targetId);
               if (targetElement) {
                   targetElement.scrollIntoView({
                       behavior: 'smooth',
                       block: 'start'
                   });
               }
           });
       });

       // Open modal and load JSP inside an isolated iframe to avoid style bleed
       function openModal(modalId, jspPath) {
           const modal = document.getElementById(modalId);
           const contentDiv = modal.querySelector(modalId === 'loginModal' ? '#loginContent' : '#registerContent');

           // Render an iframe for isolation so the loaded page's CSS doesn't override the parent
           const iframeHtml = '<iframe src="' + jspPath + '" style="width:100%;height:80vh;border:none;" loading="eager"></iframe>';
           contentDiv.innerHTML = iframeHtml;
           modal.style.display = 'block';
       }

       function closeModal(modalId) {
           document.getElementById(modalId).style.display = 'none';
       }

       //Close modal if user clicks outside
       window.onclick = function (event) {
           document.querySelectorAll('.modal').forEach(modal => {
               if (event.target === modal) {
                   modal.style.display = 'none';
               }
           });
       }

   </script>

</body>
</html>