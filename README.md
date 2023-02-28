# Cabify Mobile Challange

The application shows the list of products offered by Cabify, the user can add to the Cart and see the total of the purchase.
Every product contains information that can be seen in the Product Detail screen, along with a Promotion description in case the product has any promotion available.
In the Cart screen, the user can check and modify the quantity of the product, and remove items from the cart.
The total is calculated considering possible discounts.

The project was created following the MVVM pattern, using Retrofit to get the product list from the server, ROOM to save data in a database, Hilt for dependency injection, and coroutines for operations that have to be done apart from the UI thread
