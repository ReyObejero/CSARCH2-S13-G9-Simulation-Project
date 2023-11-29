# Project Specifications
This project is intended to simulate 8-Way Block Set Associative (BSA) Cache Mapping with the Most Recently Used (MRU) replacement policy. The project was programmed in Java, with the JavaFX library used for developing the graphical user interface (GUI). The cache simulation system follows these specifications:
 - 16 cache blocks
 - 32 words per cache line
 - Load-through read policy
 - User-specified number of memory blocks
 The 8-Way BSA equally divides these 16 cache blocks into sets that are each 8 blocks in size, thus resulting in the 2 sets used by the system.

# How to Use
For instructions on how to setup and run the source code, please refer to the [Installation Guide](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/blob/main/Installation-Guide.pdf) file included in the repository. A video Walkthrough of the cache simulation system can be viewed [here](https://www.youtube.com/watch?v=e3X7u3RuH8s).

# Test Case Analysis
These test cases use an input of *16* cache blocks--the number of cache blocks stated in the project specifications.

**Sequential Sequence**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/cd399bb2-e1e3-47d7-9879-1b2b50acfdc6)

**Random Sequence**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/28817d37-a416-486b-9558-7dd915d22e0b)

**Mid-Repeat Blocks**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/7a97aaeb-c91f-40c3-a88a-24de150c8f71)
