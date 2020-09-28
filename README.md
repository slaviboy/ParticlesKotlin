# ParticlesKotlin
Simple library for creating particle effects written in Kotlin

![cookie monster](https://github.com/slaviboy/ParticlesKotlin/blob/master/screens/home.png)

## About
ParticlesKotlin is library that is for creating particle effect that can be visualized in View, preferable type of view is SurfaceView or TextureView for the need of animating the particles. To learn more about the custom properties and how to use the library in derails read the official [wiki](https://github.com/slaviboy/ParticlesKotlin/wiki) page.
 
The library has two available types of particles
* **[Dust](https://github.com/slaviboy/ParticlesKotlin/wiki#dust-particles)** particles, are circles randomly moved and drawn in 2D. This is achieve by changing the velocity properties -vx and vy, that are used to set the new position of the particle. Customization is available via properties like color, radius, speed and many more.
* **[Line](https://github.com/slaviboy/ParticlesKotlin/wiki#line-particles)** particles, are circles randomly moved and drawn in 2D. Combined with lines that are drawn depending on the minimum and maximum distance between every two particles from the set. Particles are moved by changing the velocity using the properties -vx and vy, that sets the new position of the particle. The lines are generated responsively right after the new particle position is available. Customization is available via properties like color, radius, speed and many more.

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Download](https://img.shields.io/badge/version-0.1.0-blue)](https://github.com/slaviboy/ParticlesKotlin/releases/tag/v0.1.0)
 

## Add to your project
Add the jitpack maven repository
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
``` 
Add the dependency
```
dependencies {
  implementation 'com.github.slaviboy:ParticlesKotlin:v0.1.0'
}
```

## How to use
To generate and draw the particles create new kotlin class **ParticleView** and pass the code below. For generating particles use any of the two available generator classes [DustParticlesGenerator](https://github.com/slaviboy/ParticlesKotlin/blob/master/particles/src/main/java/com/slaviboy/particles/dust/DustParticlesGenerator.kt) and [LineParticlesGenerator](https://github.com/slaviboy/ParticlesKotlin/blob/master/particles/src/main/java/com/slaviboy/particles/line/LineParticlesGenerator.kt) depending on the type of particles you would like to generate. On the code below DustParticlesGenerator is used.

```Kotlin
class ParticleView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // create dust particle generator
    var dustParticlesGenerator: DustParticlesGenerator = DustParticlesGenerator( 
        minRadius = 0.0,
        maxRadius = 30.0,
        particlesRadialGradient = RadialGradient(
            0.0f,
            0.0f,
            30.0f,
            intArrayOf(Color.BLUE, Color.TRANSPARENT),
            floatArrayOf(0.0f, 0.9f),
            Shader.TileMode.MIRROR
        )
    )

    override fun onDraw(canvas: Canvas) {

        // set view size, must be done in initialization after the
        // view is measured, but for simplicity I set it here
        dustParticlesGenerator.viewHeight = height
        dustParticlesGenerator.viewWidth = width
 
        // update and draw particles
        dustParticlesGenerator.update()
        dustParticlesGenerator.draw(canvas, clearCanvas = true)

        // invalidate to force redrawing of the view
        invalidate()
    }
}
```

To use the premade TextureView class copy the code from the [drawing](https://github.com/slaviboy/ParticlesKotlin/tree/master/app/src/main/java/com/slaviboy/simpleparticlesexample/drawing) package to your project, in order to have access to the  class, since it is not directly located in the library. If you dont want to use the pre-made classes you can always make your own View and just use the library.  
