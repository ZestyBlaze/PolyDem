package dev.zestyblaze.demeter.compat;

import dev.zestyblaze.demeter.compat.pages.CookingRecipePage;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import eu.pb4.polydex.api.v1.recipe.PolydexPage;

public class PolydexCompatImpl {
    public static void init() {
        PolydexPage.registerRecipeViewer(CookingRecipe.class, CookingRecipePage::new);
    }
}
