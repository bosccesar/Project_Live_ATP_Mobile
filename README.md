# 0 - Hardware lié au projet
Galaxy Tab A 10.1"
Lien pour les specs nécessaires à la création de l'émulateur de la tablette :
https://www.samsung.com/us/mobile/tablets/all-other-tablets/sm-t580nzkaxar-sm-t580nzkaxar/

# 1 - Près-requis
Installer Android Studio
https://developer.android.com/studio/index.html

# 2 - Installer sdk
Tools -> Android -> Sdk Manager
Checker la checkbox "Android API 27 si non cochée"
Checker la checkbox "Android 7.1.1 (Nougat) API Level 25"
 Si erreur lancement émulateur à cause de l'HAXM (voir log), onglet SDK Tools:
- Décocher "Intel x86 Emulator Accelerator (HAXM installer)" et appliquer les modifs 
- Recochez "Intel x86 Emulator Accelerator (HAXM installer)" et appliquer les modifs

# 3 - Configuration et installation de l'émulateur
Run l'app
Create New Virtual Device
New Hardware Profile
Modifier le "screen size" (pouces de la tablette), la résolution, la ram, checker "Has Hardware Buttons" suivant les specs
Next
Download "Nougat API Level 25"
Next
Orientation Landscape
Show advanced Settings
Modifier la ram suivant les specs

# 4 - Enjoy
