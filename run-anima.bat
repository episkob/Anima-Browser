@echo off
setlocal enabledelayedexpansion

echo ================================================
echo              Anima Browser v1.0
echo ================================================
echo.
echo Starting with support for:
echo - YouTube videos and thumbnails
echo - HTML5 media content  
echo - WebGL and Canvas
echo - Video codecs H.264, VP8, VP9, AV1
echo.

REM Check Java installation
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not found in PATH
    echo Please install Java 21 or newer
    pause
    exit /b 1
)

echo Starting Anima Browser...
echo.

REM Launch with optimized settings for video and media
java ^
    -Dfile.encoding=UTF-8 ^
    -Xmx2g ^
    -XX:+UseG1GC ^
    -Dprism.lcdtext=false ^
    -Dprism.text=t2k ^
    -Djava.awt.headless=false ^
    -Djavafx.webkit.media=true ^
    -Dcom.sun.media.jfxmediaimpl.platform=PlatformFactory ^
    -Djavafx.platform.video.codecs=h264,vp8,vp9,av1 ^
    -Djavafx.media.codec.h264=true ^
    -Djavafx.media.codec.aac=true ^
    -Djavafx.media.codec.mp3=true ^
    -Dwebkit.media.mp4=true ^
    -Dwebkit.media.webm=true ^
    -Dwebkit.media.ogg=true ^
    -Dwebkit.media.hls=true ^
    -Dwebkit.media.dash=true ^
    -Dwebkit.media.enable=true ^
    -Dwebkit.media.gstreamer.enable=true ^
    -Dwebkit.media.preload=auto ^
    -Dwebkit.media.autoplay=true ^
    -Dwebkit.images.enable=true ^
    -Dwebkit.images.png=true ^
    -Dwebkit.images.jpeg=true ^
    -Dwebkit.images.webp=true ^
    -Dwebkit.canvas.enable=true ^
    -Dwebkit.canvas.2d=true ^
    -Dwebkit.webgl.enable=true ^
    -Dcom.sun.media.jfxmediaimpl.useNativeCodecs=true ^
    -Dprism.order=d3d,sw ^
    -Dprism.vsync=true ^
    -Dprism.allowhidpi=true ^
    -Djavafx.animation.fullspeed=true ^
    -Djavafx.media.quality=auto ^
    -Djavafx.webkit.network.useSystemProxies=true ^
    -Djavafx.webkit.security.allowUniversalAccessFromFileURLs=true ^
    -Djavafx.webkit.security.allowFileAccessFromFileURLs=true ^
    -Djavafx.webkit.media.crossorigin=true ^
    -Djavafx.webkit.media.cors=true ^
    -Dsun.net.client.defaultConnectTimeout=10000 ^
    -Dsun.net.client.defaultReadTimeout=30000 ^
    -Djava.net.useSystemProxies=true ^
    -jar anima-browser.jar

echo.
echo Browser closed. Thank you for using Anima Browser!
pause
