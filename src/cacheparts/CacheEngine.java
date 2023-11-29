package cacheparts;


import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.List;

public class CacheEngine
{
    private final CacheSimulator[] cacheSimulator;

    public CacheEngine(int n){
        this.cacheSimulator = new CacheSimulator[3];

        //Test Case a: Sequential Sequence
        Queue<Integer> sequentialSequence = generateSequentialSequence(n);
        cacheSimulator[0] = new CacheSimulator(sequentialSequence);

        //Test Case b: Random Sequence
        Queue<Integer> randomSequence = generateRandomSequence(n);
        cacheSimulator[1] = new CacheSimulator(randomSequence);

        // Test Case c: Mid-repeat Blocks
        Queue<Integer> midRepeatSequence = generateMidRepeatSequence(n);
        cacheSimulator[2] = new CacheSimulator(midRepeatSequence);

    }

    public boolean performStep(List<List<List<Label>>> pane){
        boolean flag = false;
        int x = 0;
        for(CacheSimulator simulator : cacheSimulator){
            flag = flag | simulator.performStep();
            //i = the two caches, 2.
            //y = the block sizes
            for(int i = 0; i < simulator.cacheSets.size(); i++){
                for(int y = 0; y < simulator.cacheSets.get(i).size(); y++){
                    String value = simulator.cacheSets.get(i).get(y).toString();
                    int index = simulator.mostRecentlyAccessed[i];
                    if(index == y){
                        value = value + ", MRU";
                    }
                    pane.get(x).get(i).get(y).setText(value);
                }
            }
            x++;
        }
        return flag;
    }
    public void performAllStep(TextArea area, List<List<List<Label>>> pane){
        int id = 0;
        StringBuilder builder = new StringBuilder();
        for(CacheSimulator simulator : cacheSimulator){
            //simulator.performAll();
            switch (id) {
                case 0 -> builder.append("Sequential Sequence");
                case 1 -> builder.append("Random Sequence");
                default -> builder.append("Mid-Repeat Sequence");
            }
            builder.append("\n");
            while (!simulator.accessSequence.isEmpty()) {
                performStep(pane);
                getIndividualInformation(id,builder);
            }
            id++;
        }
        area.appendText(builder.toString());
    }

    private void getIndividualInformation(int id, StringBuilder builder){
        CacheSimulator simulator = this.cacheSimulator[id];
        builder.append("Cache State after Step ").append(simulator.currentStep).append(": ").append(simulator.cacheSets.toString());
        builder.append("\n");
        builder.append("Metrics after Step ").append(simulator.currentStep).append(":");
        builder.append("\n");
        builder.append("\tMRU Index Set 1: ").append(simulator.mostRecentlyAccessed[0]);
        builder.append("\n");
        builder.append("\tMRU Index Set 2: ").append(simulator.mostRecentlyAccessed[1]);
        builder.append("\n");
        builder.append("\tMemory Access Count: ").append(simulator.memoryAccessCount);
        builder.append("\n");
        builder.append("\tCache Hit Count and Hit Rate: ").append(simulator.cacheHitCount).append(" and ").append(((int)((simulator.cacheHitCount/(float)simulator.memoryAccessCount) * 10000))/100F).append("%");
        builder.append("\n");
        builder.append("\tCache Miss Count and Miss Rate: ").append(simulator.cacheMissCount).append(" and ").append(((int)((simulator.cacheMissCount/(float)simulator.memoryAccessCount) * 10000))/100F).append("%");
        builder.append("\n");
        builder.append("\tTotal Memory Access Time: ").append(simulator.totalMemoryAccessTime);
        builder.append("\n");

        //72
        int average = (int)((simulator.cacheHitCount / (float)simulator.memoryAccessCount) + (simulator.cacheMissCount/ (float)simulator.memoryAccessCount) * (1 + (simulator.cacheMissCount * 10)));

        builder.append("\tAverage Memory Access Time: ").append(average);
        builder.append("\n");


    }
    public String getCurrentStepInformation(){
        StringBuilder builder = new StringBuilder();
        int id = 0;
        for(CacheSimulator simulator : cacheSimulator){
            switch (id) {
                case 0 -> builder.append("Sequential Sequence");
                case 1 -> builder.append("Random Sequence");
                default -> builder.append("Mid-Repeat Sequence");
            }
            builder.append("\n");
            id++;
            builder.append("Cache State after Step ").append(simulator.currentStep).append(": ").append(simulator.cacheSets.toString());
            builder.append("\n");
            builder.append("Metrics after Step ").append(simulator.currentStep).append(":");
            builder.append("\n");
            builder.append("\tMRU Index Set 1: ").append(simulator.mostRecentlyAccessed[0]);
            builder.append("\n");
            builder.append("\tMRU Index Set 2: ").append(simulator.mostRecentlyAccessed[1]);
            builder.append("\n");
            builder.append("\tMemory Access Count: ").append(simulator.memoryAccessCount);
            builder.append("\n");
            builder.append("\tCache Hit Count and Hit Rate: ").append(simulator.cacheHitCount).append(" and ").append(((int)((simulator.cacheHitCount/(float)simulator.memoryAccessCount) * 10000))/100F).append("%");
            builder.append("\n");
            builder.append("\tCache Miss Count and Miss Rate: ").append(simulator.cacheMissCount).append(" and ").append(((int)((simulator.cacheMissCount/(float)simulator.memoryAccessCount) * 10000))/100F).append("%");
            builder.append("\n");
            builder.append("\tTotal Memory Access Time: ").append(simulator.totalMemoryAccessTime);
            builder.append("\n");

            int average = (int)((simulator.cacheHitCount / (float)simulator.memoryAccessCount) + (simulator.cacheMissCount/ (float)simulator.memoryAccessCount) * (1 + (simulator.cacheMissCount * 10)));
            builder.append("\tAverage Memory Access Time: ").append(average);
            builder.append("\n");
        }

        return builder.toString();
    }

    public static Queue<Integer> generateSequentialSequence(int n) {
        Queue<Integer> sequence = new LinkedList<>();
        for (int i = 0; i < 4; i++) { // Repeat 4 times
            for (int j = 0; j < n*2; j++) {
                sequence.add(j);
            }
        }
        System.out.println("Sequence: " + sequence);
        return sequence;
    }

    public static Queue<Integer> generateRandomSequence(int size) {
        Queue<Integer> sequence = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < size*4; i++) {
            sequence.add(random.nextInt(32));
        }
        System.out.println("Sequence: " + sequence);
        return sequence;
    }
    public static Queue<Integer> generateCustom(int n) {
        Queue<Integer> sequence = new LinkedList<>();
        for(int i = 0; i < 16; i++){
            sequence.add(i);
        }
        sequence.add(0);
        sequence.add(1);
        for(int i = 16; i < 32; i++){
            sequence.add(i);
        }

        System.out.println("Sequence: " + sequence);
        return sequence;
    }

    private static Queue<Integer> generateMidRepeatSequence(int n) {
        Queue<Integer> sequence = new LinkedList<>();
        for (int i = 0; i < 4; i++) { // Repeat 4 times
            // Start with 0
            sequence.add(0);
            // Repeat the middle sequence (numbers 1 to n-1) twice
            for(int z = 0; z < 2; z ++){
                int loop = z == 1 ? n : n - 1;
                for (int j = 1; j < loop; j++) {
                    sequence.add(j);
                }
            }
            // Continue the sequence up to 2n-1
            for (int j = n; j < 2 * n; j++) {
                sequence.add(j);
            }
        }
        System.out.println("Sequence: " + sequence);
        return sequence;
    }
    public static class CacheSimulator {
        private static final int CACHE_LINES_PER_BLOCK = 32; // 32 words per cache line
        private static final int NUMBER_OF_CACHE_BLOCKS = 16; // Total number of cache blocks
        private final List<List<Integer>> cacheSets; // 8-way set associative cache
        private static final int EIGHT_WAY = 8;

        private final Queue<Integer> accessSequence;
        private int memoryAccessCount = 0;
        private int cacheHitCount = 0;
        private int cacheMissCount = 0;
        private int totalMemoryAccessTime = 0;
        private int currentStep = 0;

        private final int [] mostRecentlyAccessed;

        public CacheSimulator(Queue<Integer> accessSequence) {
            int numberOfSets = NUMBER_OF_CACHE_BLOCKS / EIGHT_WAY; // Assuming 8-way set associative cache

            this.accessSequence = new LinkedList<>(accessSequence);
            this.cacheSets = new ArrayList<>(numberOfSets);
            for (int i = 0; i < numberOfSets; i++) {
                cacheSets.add(new ArrayList<>()); // Initialize each set
            }
            mostRecentlyAccessed = new int[this.cacheSets.size()];
        }

        public boolean performStep() {
            if (accessSequence.isEmpty()) {
                return false;
            }

            int blockToAccess = accessSequence.poll();
            processBlockAccess(blockToAccess);

            currentStep++;
            return true;
        }
        private void processBlockAccess(int blockToAccess) {
            int setIndex = blockToAccess % cacheSets.size(); //4%2=0
            List<Integer> set = cacheSets.get(setIndex); //0

            memoryAccessCount++;

            boolean isHit = set.contains(blockToAccess);//does have 4?
            if (isHit) {
                mostRecentlyAccessed[setIndex] = set.indexOf(blockToAccess);
                cacheHitCount++;
                totalMemoryAccessTime = (cacheHitCount * 2) + (cacheMissCount * 2 * 11) + (cacheMissCount); // Assumed time for memory access
            } else {
                cacheMissCount++;
                totalMemoryAccessTime = (cacheHitCount * 2) + (cacheMissCount * 2 * 11) + (cacheMissCount); // Assumed time for memory access
                //Fill in while not yet full...
                if(set.size() < EIGHT_WAY){
                    mostRecentlyAccessed[setIndex] = set.size();
                    set.add(blockToAccess);
                }
                else {
                    //Remove the most recently used index, and replace it with the memory queried
                    set.remove(mostRecentlyAccessed[setIndex]);
                    set.add(mostRecentlyAccessed[setIndex], blockToAccess);
                }

            }
        }

    }
}



