# StfDashboardUi

# Project Backend URL

This project provides the API url in the `app.module.ts` file - the token is defined in the `tokens.ts` file, but the actual value is provided in the module definition.

# General Project Structure

The project is generally laid out similarly to the backend - repositories to communicate with the API, models to store information from the API, components for general use across the platform, and pages as components linked to specific routes.

## Notable components:

The grid component handles rendering an abstract concept of a grid with mutable features to allow for reuse across the site, since most pages rely on a grid of some kind.

The weather icon component allows for simple abstraction of the concept of a weather icon, but the calculation is still done before passing to the component itself.
