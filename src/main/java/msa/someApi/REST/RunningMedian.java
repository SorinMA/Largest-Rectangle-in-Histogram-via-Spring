package msa.someApi.REST;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunningMedian {
    @RequestMapping(value="/runningmedian")
    public String runningMedian() throws IOException {
        return "Given an input stream of  integers, you must perform the following task for each  integer: Add the  integer to a running list of integers. Find the median of the updated list (i.e., for the first element through the  element). Print the list's updated median on a new line. The printed value must be a double-precision number scaled to  decimal place (i.e.,  format).";
    }

    @RequestMapping(value="/runningmedian", method = RequestMethod.POST)
    public double[] runningMedian(@RequestBody int[] a) throws IOException {
        // store ( > median) elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        // store ( <= median) elements
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(
            Comparator.reverseOrder()
        );

        int lenA = a.length;
        double lastMedian = -1;
        double[] res = new double[lenA];
        int sizeMinHeap = 0;
        int sizeMaxHeap = 0;

        for (int i = 0 ; i < lenA ; i += 1) {
            if (a[i] > lastMedian) {
                minHeap.add(a[i]);
                sizeMinHeap += 1;
            } else {
                maxHeap.add(a[i]);
                sizeMaxHeap += 1;
            }
            
            if (Math.abs(sizeMaxHeap - sizeMinHeap) > 1) {
                if (sizeMaxHeap > sizeMinHeap) {
                    sizeMaxHeap -= 1;
                    sizeMinHeap += 1;
                    minHeap.add(maxHeap.remove());
                } else {
                    sizeMinHeap -= 1;
                    sizeMaxHeap += 1;
                    maxHeap.add(minHeap.remove());
                }
            }

            if (sizeMaxHeap == sizeMinHeap) {
                lastMedian = ((double) (minHeap.peek() + maxHeap.peek())) / 2;
            } else if (sizeMaxHeap > sizeMinHeap) {
                lastMedian = (double) maxHeap.peek();
            } else {
                lastMedian = (double) minHeap.peek();
            }

            res[i] = lastMedian;
        }

        return res;
    }
}
