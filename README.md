# Anima Browser
**Enterprise Web Browser built with JavaFX 21**

## Overview
Anima Browser is a modern desktop web browser application built on JavaFX 21 platform. It provides secure web browsing capabilities with enhanced media support and performance optimization.

## Key Features
- Multi-tab browsing interface
- Advanced video codec support (H.264, VP8, VP9, AV1)
- YouTube and HTML5 media optimization
- Hardware-accelerated video decoding
- WebGL and Canvas graphics support
- Integrated navigation controls
- Performance-optimized rendering engine

## Installation

### Windows Deployment
Execute the following batch files:
```
run-anima.bat      # Full feature deployment
run-simple.bat     # Standard deployment
```

### Command Line Execution
```bash
java -jar anima-browser.jar
```

## System Requirements
- Java Runtime Environment 21 or higher
- Windows 10/11 operating system
- Minimum 2GB RAM (4GB recommended)
- DirectX compatible graphics adapter

## Build Configuration
```bash
git clone https://github.com/episkob/Anima-Browser.git
cd Anima-Browser
mvnw.cmd clean package
```

## Technical Specifications

### Performance Optimization
- G1 Garbage Collection algorithm
- 2GB heap memory allocation
- Direct3D GPU acceleration
- HiDPI display support
- Optimized video codec processing

### Media Support
- Video formats: MP4, WebM, OGG, AVI, MOV, WMV, FLV
- Audio codecs: AAC, MP3, OGG Vorbis
- Streaming protocols: YouTube, HTML5 video, WebRTC
- Graphics rendering: WebGL, Canvas 2D, SVG

## License
Open source software project

---
**Powered by JavaFX 21 Platform**