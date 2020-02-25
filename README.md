# SimpleParticlesAndroid
Simple library for creating particles written in Kotlin

![cookie monster](https://github.com/slaviboy/SimpleParticlesAndroid/blob/master/screens/home1.gif)

## About
The library has two available types of particles
* **Dust**
* **Line**

[Dust](https://github.com/slaviboy/SimpleParticlesAndroid/wiki#dust-particles) particles, are circles randomly moved and drawn in 2D. This is achieve by changing the velocity properties -vx and vy, that are used to set the new position of the particle. Customization is available via properties like color, radius, speed and many more.

[Line](https://github.com/slaviboy/SimpleParticlesAndroid/wiki#line-particles) particles, are circles randomly moved and drawn in 2D. Combined with lines that are drawn depending on the minimum and maximum distance between every two particles from the set. Particles are moved by changing the velocity using the properties -vx and vy, that sets the new position of the particle. The lines are generated responsively right after the new particle position is available. Customization is available via properties like color, radius, speed and many more.


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
  implementation 'com.github.slaviboy:SimpleParticlesAndroid:v0.1.0'
}
```

## How to use
Read the [Wiki](https://github.com/slaviboy/SimpleParticlesAndroid/wiki) page for this particular library to learn more about the custom properties, that are available.
Here is how to  create a TextureView from the [example](https://github.com/slaviboy/SimpleParticlesAndroid/tree/master/app/src/main/java/com/slaviboy/simpleparticlesexample)

First create the TextureView in your xml.
```xml
<com.slaviboy.simpleparticlesexample.drawing.TextureView
    android:id="@+id/canvas"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

Second get your TextureView from your kotlin, and set your custom particle set.
```Kotlin
// set new custom particle set
val textureView = findViewById(R.id.canvas)
textureView.lineParticles = com.slaviboy.particles.line.Particles( 
     radius = 50.0f,
     particleColor: Color.GREEN,
     speed = 2.0f
)

```
