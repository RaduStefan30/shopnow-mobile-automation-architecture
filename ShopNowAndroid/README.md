ShopNow Mobile — QA Mobile Architect Exercise

This repository contains a demo mobile e-commerce application built to demonstrate QA strategy, testability, automation readiness, async handling, and performance considerations, as requested in the QA Mobile Architect technical exercise.

The project focuses on quality architecture, not production completeness.

📱 Application Overview

ShopNow Mobile is a fictional mobile e-commerce application designed for quick purchases from a smartphone.

The app allows users to:

Log in

Browse a product catalog

View product details

Add / remove favourite products

Refresh content manually

Manage basic user settings

The backend is intentionally fake and deterministic, enabling reliable testing and reproducibility.

🧩 Implemented Features
1. Login

Email & password input

Valid / invalid authentication handling

Error message displayed on invalid credentials

Navigation to Products on success

Authentication is intentionally simplified (hardcoded credentials) to focus on testing behavior rather than security.

2. Product List

Product cards with image, name, description, and price

Manual refresh via pull-to-refresh

Loading indicator during data fetch (simulated)

Backend delay simulated via coroutines

Navigation to Product Detail

3. Product Detail

Product image (fixed aspect ratio)

Name, description, and price

Favourite toggle (heart icon)

4. Favourites

Session-level persistence (in-memory)

Add / remove favourites from:

Product List

Product Detail

Favourite state reflected consistently across screens

5. Settings

Accessible from Product List (top-right icon)

User information (email only for now)

Dark theme toggle (UI only, non-functional)

Log out (session reset)