package com.atp.live_atp_mobile;

/**
 * Created by cebosc on 08/03/2018.
 */

public enum Category {
    SIMPLE_MESSIEURS("Simple messieurs", 1),
    SIMPLE_DAMES("Simple dames", 1),
    DOUBLE_MESSIEURS("Double messieurs", 2),
    DOUBLE_DAMES("Double dames", 2),
    DOUBLE_MIXTE("Double mixte", 2);

    private final String nom;
    private final int type;

    Category(String nom, int type) {
        this.nom = nom;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public int getType() {
        return this.type;
    }

    public static Category getTypeByName(String type) { //Parcours toutes les catégories
        Category category = null;
        int i = 0;
        while (i < values().length && category == null) {
            if (values()[i].nom.equals(type)) {
                category = values()[i];
            }
            i++;
        }
        return category;
    }
}
