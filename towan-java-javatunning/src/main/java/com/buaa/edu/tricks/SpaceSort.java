package com.buaa.edu.tricks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * -Xmx512M -Xms512M
 * @author Administrator
 *
 */
public class SpaceSort {
	public static int arrayLen = 1000000;

	public static void main(String[] args) {
		int[] a = new int[arrayLen];
		int[] old = new int[arrayLen];
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		int count = 0;
		while (count < a.length) {
			int value = (int) (Math.random() * arrayLen * 10) + 1;
			if (map.get(value) == null) {
				map.put(value, value);
				a[count] = value;
				count++;
			}
		}
		System.arraycopy(a, 0, old, 0, a.length);
		long start = System.currentTimeMillis();
		Arrays.sort(a);
		System.out.println("Arrays.sort spend:"+(System.currentTimeMillis() - start) + " ms");
		// outputArray(a);
		System.arraycopy(old, 0, a, 0, old.length);
		start = System.currentTimeMillis();
		spaceToTime(a);
		System.out.println("spaceToTime spend:"+(System.currentTimeMillis() - start) + " ms");
		// outputArray(a);
	}

	public static void outputArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	public static void spaceToTime(int[] array) {
		int i = 0;
		int max = array[0];
		int l = array.length;
		for (i = 1; i < l; i++)
			if (array[i] > max)			// �ҳ����ֵ
				max = array[i];
		int[] temp = new int[max + 1]; 	
		for (i = 0; i < l; i++)
			temp[array[i]] = array[i];	
		int j = 0;
		int max1 = max + 1;
		for (i = 0; i < max1; i++) { 	
			if (temp[i] > 0) {
				array[j++] = temp[i];
			}
		}
	}
}
