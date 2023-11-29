# Project Specifications
This project is intended to simulate 8-Way Block Set Associative (BSA) Cache Mapping with the Most Recently Used (MRU) replacement policy. The project was programmed in Java, with the JavaFX library used for developing the graphical user interface (GUI). The cache simulation system follows these specifications:
 - 16 cache blocks.
    - The 8-Way BSA divides these 16 cache blocks into sets that contain eight blocks each, resulting in the 2 sets shown in the video Walkthrough linked below.
 - 32 words per cache line
 - Load-through read policy
 - User-specified number of memory blocks

# How to Use
For instructions on how to setup and run the source code, please refer to the [Installation Guide](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/blob/main/Installation-Guide.pdf) file included in the repository. A video Walkthrough of the cache simulation system can be viewed [here](https://www.youtube.com/watch?v=e3X7u3RuH8s).

# Test Case Analysis
These test cases use an input of _n_, which is the number of cache blocks stated in the project specifications.

**Sequential Sequence**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/cd399bb2-e1e3-47d7-9879-1b2b50acfdc6)

The Sequential Sequence test case uses a sequence of 0 to 31 (2n) that is repeated four times. For the first run through, the system simply maps 0 to 31 to the cache with 0 misses and its replacements are limited to the eighth blocks in both sets because these blocks are the most recently used. For the second run through, the system maps 0 to 13 from the first block until the seventh blocks for both sets because these numbers are hits since only the eight blocks in both sets were replaced previously. Then, MRU continues replacing the seventh blocks for both sets until the next hits, which are 30 and 31 in the eigth blocks from the previous run through. For the third run through, the system maps 0 to 11 from the first block until the sixth blocks for both sets because these numbers are a hit. Then, the system repeatedly replaces the sixth block until the next hits, which are 28 and 29 in the seventh blocks and 30 and 31 in the eighth blocks from the previous run through. Continuing the pattern, the system maps 0 to 9 from the first block until the fifth block for both sets. Then, it repeatedly replaces the fifth block until the next hits, which are 26 and 27 in the sixth blocks, 28 and 29 in the seventh blocks, and 30 and 31 in the eigth blocks from the previous run through. In total, there were a total of 48 hits (37.5% rate) and 80 misses (62.5% rate). After the final run through, the total memory access time is 1936ns and the average memory access time is 501ns.

**Random Sequence**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/28817d37-a416-486b-9558-7dd915d22e0b)

The Random Sequence test case uses a sequence totaling 64 blocks (4n) that are ordered randomly, which results in essentially no replacement patterns and therefore random average and total access times from the random misses.

**Mid-Repeat Blocks**

![image](https://github.com/ReyObejero/CSARCH2-S13-G9-Simulation-Project/assets/107678700/7a97aaeb-c91f-40c3-a88a-24de150c8f71)

This test case is repeated four times. For the first run through, the cache system fills up blocks one through seven of both sets and the eighth block of the for the middle sequences because 0 to 14 are all misses first then they are all hits. Then, the system again goes through blocks one through eight for the hits from 0 to 15 and then repeatedly replaces the eighth block because of MRU until the next hit in the next run through. This test case has a similar pattern to the Sequential Sequence test case in the sense that for every run through, the block at which the system stays for a period before eventually reaching the final blocks becomes lesser. For the second, it stays at block seven, then at the third it stays at block six.
