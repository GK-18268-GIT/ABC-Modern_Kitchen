<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Modern Kitchen</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/takeaway.css">

</head>

<body>
    <div class="container">
        <header>
            <h1>Take Away - Dishes</h1>
            <p>ABC Modern Kitchen</p>
        </header>

        <!-- Fried Rice Section -->
        <div class="menu-section">
            <h2>Fried Rice</h2>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Chicken Fried Rice</div>
                    <img src="assets/dishes/Fried_Rice_1.png" alt="Chicken Fried Rice" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="chickenFriedRiceSize" id="chickenFriedRiceNormal" value="Normal" checked>
                                <label for="chickenFriedRiceNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="chickenFriedRiceSize" id="chickenFriedRiceLarge" value="Large">
                                <label for="chickenFriedRiceLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1000</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="chickenFriedRiceQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Chicken Fried Rice', 'chickenFriedRice')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Egg Fried Rice</div>
                    <img src="assets/dishes/Fried_Rice_2.png" alt="Egg Fried Rice" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="eggFriedRiceSize" id="eggFriedRiceNormal" value="Normal" checked>
                                <label for="eggFriedRiceNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 700</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="eggFriedRiceSize" id="eggFriedRiceLarge" value="Large">
                                <label for="eggFriedRiceLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="eggFriedRiceQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Egg Fried Rice', 'eggFriedRice')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Vegetable Fried Rice</div>
                    <img src="assets/dishes/Fried_Rice_3.png" alt="Vegetable Fried Rice" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="vegetableFriedRiceSize" id="vegetableFriedRiceNormal" value="Normal" checked>
                                <label for="vegetableFriedRiceNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 750</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="vegetableFriedRiceSize" id="vegetableFriedRiceLarge" value="Large">
                                <label for="vegetableFriedRiceLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="vegetableFriedRiceQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Vegetable Fried Rice', 'vegetableFriedRice')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Nasi Goreng</div>
                    <img src="assets/dishes/Fried_Rice_4.png" alt="Nasi Goreng" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="nasiGorengSize" id="nasiGorengNormal" value="Normal" checked>
                                <label for="nasiGorengNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 1500</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="nasiGorengSize" id="nasiGorengLarge" value="Large">
                                <label for="nasiGorengLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1750</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="nasiGorengQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Nasi Goreng', 'nasiGoreng')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Seafood Fried Rice</div>
                    <img src="assets/dishes/Fried_Rice_5.png" alt="Seafood Fried Rice" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="seafoodFriedRiceSize" id="seafoodFriedRiceNormal" value="Normal" checked>
                                <label for="seafoodFriedRiceNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 1650</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="seafoodFriedRiceSize" id="seafoodFriedRiceLarge" value="Large">
                                <label for="seafoodFriedRiceLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1850</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="seafoodFriedRiceQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Seafood Fried Rice', 'seafoodFriedRice')">Add</button>
                </div>
            </div>
        </div>

        <!-- Pasta Section -->
        <div class="menu-section">
            <h2>Pasta</h2>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Spaghetti</div>
                    <img src="assets/dishes/Pasta_1.png" alt="Spaghetti" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="spaghettiSize" id="spaghettiNormal" value="Normal" checked>
                                <label for="spaghettiNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 750</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="spaghettiSize" id="spaghettiLarge" value="Large">
                                <label for="spaghettiLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 850</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="spaghettiQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Spaghetti', 'spaghetti')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Fusilli</div>
                    <img src="assets/dishes/Pasta_2.png" alt="Fusilli" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="fusilliSize" id="fusilliNormal" value="Normal" checked>
                                <label for="fusilliNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="fusilliSize" id="fusilliLarge" value="Large">
                                <label for="fusilliLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 900</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="fusilliQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Fusilli', 'fusilli')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Ravioli</div>
                    <img src="assets/dishes/Pasta_3.png" alt="Ravioli" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="ravioliSize" id="ravioliNormal" value="Normal" checked>
                                <label for="ravioliNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 500</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="ravioliSize" id="ravioliLarge" value="Large">
                                <label for="ravioliLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 700</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="ravioliQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Ravioli', 'ravioli')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Bucatini</div>
                    <img src="assets/dishes/Pasta_4.png" alt="Bucatini" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="bucatiniSize" id="bucatiniNormal" value="Normal" checked>
                                <label for="bucatiniNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="bucatiniSize" id="bucatiniLarge" value="Large">
                                <label for="bucatiniLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1000</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="bucatiniQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Bucatini', 'bucatini')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Lasagna</div>
                    <img src="assets/dishes/Pasta_5.png" alt="Lasagna" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="lasagnaSize" id="lasagnaNormal" value="Normal" checked>
                                <label for="lasagnaNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 1000</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="lasagnaSize" id="lasagnaLarge" value="Large">
                                <label for="lasagnaLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1200</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="lasagnaQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Lasagna', 'lasagna')">Add</button>
                </div>
            </div>
        </div>

        <!-- Noodles Section -->
        <div class="menu-section">
            <h2>Noodles</h2>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Chicken Noodles</div>
                    <img src="assets/dishes/Noodles_1.png" alt="Chicken Noodles" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="chickenNoodlesSize" id="chickenNoodlesNormal" value="Normal" checked>
                                <label for="chickenNoodlesNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 750</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="chickenNoodlesSize" id="chickenNoodlesLarge" value="Large">
                                <label for="chickenNoodlesLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 850</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="chickenNoodlesQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Chicken Noodles', 'chickenNoodles')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Ramen</div>
                    <img src="assets/dishes/Noodles_2.png" alt="Ramen" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="ramenSize" id="ramenNormal" value="Normal" checked>
                                <label for="ramenNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 950</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="ramenSize" id="ramenLarge" value="Large">
                                <label for="ramenLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1000</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="ramenQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Ramen', 'ramen')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Lamian</div>
                    <img src="assets/dishes/Noodles_3.png" alt="Lamian" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="lamianSize" id="lamianNormal" value="Normal" checked>
                                <label for="lamianNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 1100</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="lamianSize" id="lamianLarge" value="Large">
                                <label for="lamianLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1250</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="lamianQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Lamian', 'lamian')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Vegetable Soup Noodles</div>
                    <img src="assets/dishes/Noodles_4.png" alt="Vegetable Soup Noodles" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="vegetableSoupNoodlesSize" id="vegetableSoupNoodlesNormal" value="Normal" checked>
                                <label for="vegetableSoupNoodlesNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 800</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="vegetableSoupNoodlesSize" id="vegetableSoupNoodlesLarge" value="Large">
                                <label for="vegetableSoupNoodlesLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 900</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="vegetableSoupNoodlesQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Vegetable Soup Noodles', 'vegetableSoupNoodles')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Garak Guksu</div>
                    <img src="assets/dishes/Noodles_5.png" alt="Garak Guksu" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="size-options">
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="garakGuksuSize" id="garakGuksuNormal" value="Normal" checked>
                                <label for="garakGuksuNormal">Normal</label>
                            </div>
                            <div class="size-price">Rs: 1250</div>
                        </div>
                        <div class="size-option">
                            <div class="size-box">
                                <input type="radio" name="garakGuksuSize" id="garakGuksuLarge" value="Large">
                                <label for="garakGuksuLarge">Large</label>
                            </div>
                            <div class="size-price">Rs: 1450</div>
                        </div>
                    </div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="garakGuksuQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Garak Guksu', 'garakGuksu')">Add</button>
                </div>
            </div>
        </div>

        <!-- Soup Section -->
        <div class="menu-section">
            <h2>Soup</h2>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Chicken Soup</div>
                    <img src="assets/dishes/Soup_1.png" alt="Chicken Soup" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 700</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="chickenSoupQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Chicken Soup', 'chickenSoup')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Vegetable Soup</div>
                    <img src="assets/dishes/Soup_2.png" alt="Vegetable Soup" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 600</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="vegetableSoupQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Vegetable Soup', 'vegetableSoup')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Corn Chowder Soup</div>
                    <img src="assets/dishes/Soup_3.png" alt="Corn Chowder Soup" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 660</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="cornChowderSoupQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Corn Chowder Soup', 'cornChowderSoup')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Potato Soup</div>
                    <img src="assets/dishes/Soup_4.png" alt="Potato Soup" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 550</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="potatoSoupQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Potato Soup', 'potatoSoup')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Borscht</div>
                    <img src="assets/dishes/Soup_5.png" alt="Borscht" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 800</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="borschtQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Borscht', 'borscht')">Add</button>
                </div>
            </div>
        </div>

        <!-- Sandwiches Section -->
        <div class="menu-section">
            <h2>Sandwiches</h2>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Chicken Sandwich</div>
                    <img src="assets/dishes/Sandwich_1.png" alt="Chicken Sandwich" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 600</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="chickenSandwichQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Chicken Sandwich', 'chickenSandwich')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Club Sandwich</div>
                    <img src="assets/dishes/Sandwich_2.png" alt="Club Sandwich" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 800</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="clubSandwichQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Club Sandwich', 'clubSandwich')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Italian Sandwich</div>
                    <img src="assets/dishes/Sandwich_3.png" alt="Italian Sandwich" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 800</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="italianSandwichQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Italian Sandwich', 'italianSandwich')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Grilled Cheese Sandwich</div>
                    <img src="assets/dishes/Sandwich_4.png" alt="Grilled Cheese Sandwich" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 800</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="grilledCheeseSandwichQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Grilled Cheese Sandwich', 'grilledCheeseSandwich')">Add</button>
                </div>
            </div>

            <div class="dish-item">
                <div class="dish-info">
                    <div class="dish-name">Chicken Caprese Sandwich</div>
                    <img src="assets/dishes/Sandwich_5.png" alt="Chicken Caprese Sandwich" class="dish-image">
                </div>
                <div class="size-options-container">
                    <div class="no-size-price">Rs: 800</div>
                </div>
                <div class="quantity-controls">
                    <input type="number" class="quantity-input" id="chickenCapreseSandwichQty" min="1" value="1">
                    <button class="add-btn" onclick="addToCart('Chicken Caprese Sandwich', 'chickenCapreseSandwich')">Add</button>
                </div>
            </div>
        </div>

        <!-- Checkout Table -->
        <div class="checkout-section">
            <h2>Check out table</h2>
            <table id="checkoutTable">
                <thead>
                    <tr>
                        <th>Dish Name</th>
                        <th>Number of dishes with size(N/L)</th>
                        <th>Price (Rs.)</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="checkoutTableBody">
                    <tr class="empty-cart">
                        <td colspan="4">No items added yet</td>
                    </tr>
                </tbody>
            </table>

            <!-- Place Order Button -->
            <div class="place-order-section">
                <button class="place-order-btn" id="placeOrderBtn" onclick="placeOrder()" disabled>Place Order</button>
            </div>
        </div>
    </div>

    <script>
        let cartItems = [];
        let itemIdCounter = 1;

        const prices = {
            'Chicken Fried Rice': { 'Normal': 800, 'Large': 1000 },
            'Egg Fried Rice': { 'Normal': 700, 'Large': 800 },
            'Vegetable Fried Rice': { 'Normal': 750, 'Large': 800 },
            'Nasi Goreng': { 'Normal': 1500, 'Large': 1750 },
            'Seafood Fried Rice': { 'Normal': 1650, 'Large': 1850 },
            'Spaghetti': { 'Normal': 750, 'Large': 850 },
            'Fusilli': { 'Normal': 800, 'Large': 900 },
            'Ravioli': { 'Normal': 500, 'Large': 700 },
            'Bucatini': { 'Normal': 800, 'Large': 1000 },
            'Lasagna': { 'Normal': 1000, 'Large': 1200 },
            'Chicken Noodles': { 'Normal': 750, 'Large': 850 },
            'Ramen': { 'Normal': 950, 'Large': 1000 },
            'Lamian': { 'Normal': 1100, 'Large': 1250 },
            'Vegetable Soup Noodles': { 'Normal': 800, 'Large': 900 },
            'Garak Guksu': { 'Normal': 1250, 'Large': 1450 },
            'Chicken Soup': 700,
            'Vegetable Soup': 600,
            'Corn Chowder Soup': 660,
            'Potato Soup': 550,
            'Borscht': 800,
            'Chicken Sandwich': 600,
            'Club Sandwich': 800,
            'Italian Sandwich': 800,
            'Grilled Cheese Sandwich': 800,
            'Chicken Caprese Sandwich': 800
        };

        const dishImages = {
            'Chicken Fried Rice': 'assets/dishes/Fried_Rice_1.png',
            'Egg Fried Rice': 'assets/dishes/Fried_Rice_2.png',
            'Vegetable Fried Rice': 'assets/dishes/Fried_Rice_3.png',
            'Nasi Goreng': 'assets/dishes/Fried_Rice_4.png',
            'Seafood Fried Rice': 'assets/dishes/Fried_Rice_5.png',
            'Spaghetti': 'assets/dishes/Pasta_1.png',
            'Fusilli': 'assets/dishes/Pasta_2.png',
            'Ravioli': 'assets/dishes/Pasta_3.png',
            'Bucatini': 'assets/dishes/Pasta_4.png',
            'Lasagna': 'assets/dishes/Pasta_5.png',
            'Chicken Noodles': 'assets/dishes/Noodles_1.png',
            'Ramen': 'assets/dishes/Noodles_2.png',
            'Lamian': 'assets/dishes/Noodles_3.png',
            'Vegetable Soup Noodles': 'assets/dishes/Noodles_4.png',
            'Garak Guksu': 'assets/dishes/Noodles_5.png',
            'Chicken Soup': 'assets/dishes/Soup_1.png',
            'Vegetable Soup': 'assets/dishes/Soup_2.png',
            'Corn Chowder Soup': 'assets/dishes/Soup_3.png',
            'Potato Soup': 'assets/dishes/Soup_4.png',
            'Borscht': 'assets/dishes/Soup_5.png',
            'Chicken Sandwich': 'assets/dishes/Sandwich_1.png',
            'Club Sandwich': 'assets/dishes/Sandwich_2.png',
            'Italian Sandwich': 'assets/dishes/Sandwich_3.png',
            'Grilled Cheese Sandwich': 'assets/dishes/Sandwich_4.png',
            'Chicken Caprese Sandwich': 'assets/dishes/Sandwich_5.png'
        };

        function addToCart(dishName, dishId) {
            // Get selected size
            let size = 'Normal'; // Default
            const sizeRadios = document.querySelectorAll('input[name="' + dishId + 'Size"]:checked');
            if (sizeRadios.length > 0) {
                size = sizeRadios[0].value;
            }

            // Get quantity
            const quantity = parseInt(document.getElementById(dishId + 'Qty').value);

            // Calculate price
            let price;
            if (typeof prices[dishName] === 'object') {
                price = prices[dishName][size];
            } else {
                price = prices[dishName];
                size = ''; // No size for items like soup and sandwiches
            }

            const totalPrice = price * quantity;

            // Add to cart
            const item = {
                id: itemIdCounter++,
                name: dishName,
                size: size,
                quantity: quantity,
                price: price,
                totalPrice: totalPrice,
                image: dishImages[dishName]
            };

            cartItems.push(item);
            updateCheckoutTable();

            // Reset quantity to 1
            document.getElementById(dishId + 'Qty').value = 1;

            // Enable place order button
            document.getElementById('placeOrderBtn').disabled = false;
        }

        function updateCheckoutTable() {
            const tableBody = document.getElementById('checkoutTableBody');
            tableBody.innerHTML = '';

            if (cartItems.length === 0) {
                tableBody.innerHTML = '<tr class="empty-cart"><td colspan="4">No items added yet</td></tr>';
                document.getElementById('placeOrderBtn').disabled = true;
                return;
            }

            let grandTotal = 0;

            cartItems.forEach(function(item) {
                const row = document.createElement('tr');

                // Dish name with image
                const nameCell = document.createElement('td');
                const dishInfoDiv = document.createElement('div');
                dishInfoDiv.className = 'cart-dish-info';

                const img = document.createElement('img');
                img.src = item.image;
                img.alt = item.name;
                img.className = 'cart-item-image';

                const nameSpan = document.createElement('span');
                nameSpan.textContent = item.name;

                dishInfoDiv.appendChild(img);
                dishInfoDiv.appendChild(nameSpan);
                nameCell.appendChild(dishInfoDiv);
                row.appendChild(nameCell);

                // Quantity and size
                const quantityCell = document.createElement('td');
                if (item.size && item.size !== '') {
                    quantityCell.textContent = item.quantity + ' (' + item.size + ')';
                } else {
                    quantityCell.textContent = item.quantity;
                }
                row.appendChild(quantityCell);

                // Price
                const priceCell = document.createElement('td');
                priceCell.textContent = item.totalPrice;
                row.appendChild(priceCell);

                // Action buttons
                const actionCell = document.createElement('td');
                actionCell.className = 'action-buttons';

                const updateButton = document.createElement('button');
                updateButton.textContent = 'Update';
                updateButton.className = 'update-btn';
                updateButton.onclick = function () { updateItem(item.id); };

                const removeButton = document.createElement('button');
                removeButton.textContent = 'Remove';
                removeButton.className = 'remove-btn';
                removeButton.onclick = function () { removeItem(item.id); };

                actionCell.appendChild(updateButton);
                actionCell.appendChild(removeButton);
                row.appendChild(actionCell);

                tableBody.appendChild(row);

                grandTotal += item.totalPrice;
            });

            // Add total row
            const totalRow = document.createElement('tr');
            totalRow.className = 'total-row';

            const emptyCell1 = document.createElement('td');
            const emptyCell2 = document.createElement('td');
            const totalLabelCell = document.createElement('td');
            totalLabelCell.textContent = 'Total:';
            totalLabelCell.style.textAlign = 'right';

            const totalValueCell = document.createElement('td');
            totalValueCell.textContent = grandTotal;

            totalRow.appendChild(emptyCell1);
            totalRow.appendChild(emptyCell2);
            totalRow.appendChild(totalLabelCell);
            totalRow.appendChild(totalValueCell);

            tableBody.appendChild(totalRow);
        }

        function removeItem(id) {
            cartItems = cartItems.filter(function(item) { return item.id !== id; });
            updateCheckoutTable();
        }

        function updateItem(id) {
            const item = cartItems.find(function(item) { return item.id === id; });
            if (item) {
                const newQuantity = prompt('Enter new quantity for ' + item.name + ':', item.quantity);
                if (newQuantity !== null && !isNaN(newQuantity) && newQuantity > 0) {
                    item.quantity = parseInt(newQuantity);
                    item.totalPrice = item.price * item.quantity;
                    updateCheckoutTable();
                }
            }
        }

        function placeOrder() {
            if (cartItems.length === 0) {
                alert('Your cart is empty. Please add items before placing an order.');
                return;
            }

            // Calculate total
            let total = 0;
            cartItems.forEach(function(item) {
                total += item.totalPrice;
            });

            // Show confirmation
            const confirmOrder = confirm('Your total is Rs. ' + total + '. Do you want to place this order?');

            if (confirmOrder) {
                // In a real application, you would send this data to the server
                alert('Your order has been placed successfully! Thank you for your order.');

                // Clear the cart
                cartItems = [];
                updateCheckoutTable();

                // Disable the place order button
                document.getElementById('placeOrderBtn').disabled = true;
            }
        }
    </script>
</body>
</html>