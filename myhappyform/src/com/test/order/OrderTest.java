package com.test.order;

import java.util.Arrays;

public class OrderTest {

	public static void main(String[] args) {
		int[] arr= {1,4,84,50,49,67,32,18,2,6};
		int[] arr2=new int[]{1,4,84,50,49,67,32,18,2,6};
		insert_order(arr,arr2);
	}
	
	/**
	 * 1. 从第一个元素开始，该元素可以认为已经被排序
     * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 5. 将新元素插入到该位置后
     * 6. 重复步骤2~5
     * 二分法变种，始终保证第一层循环变量参数之前是已排序的元素
	 * @Title insert_order
	 * @param arr
	 * @param arr2
	 * @author zpj
	 * @time 2019年11月11日 下午4:26:29
	 */
	public static void insert_order(int[] arr,int[] arr2){
		System.out.println("原数组");
		System.out.println(Arrays.toString(arr2));
		System.out.println("现数组");
		for(int m=0;m<arr.length-1;m++){
//			System.out.println("外循环");
			for(int n=m+1;n>0;n--){
//				System.out.println("內循环");
				if(arr[n-1]>arr[n]){
					int temp=arr[n-1];
					arr[n-1]=arr[n];
					arr[n]=temp;
					System.out.println(Arrays.toString(arr));
				}
				
			}
			
		}
		
//		for( int i=0; i<arr.length-1; i++ ) {
//            for( int j=i+1; j>0; j-- ) {
//                if( arr[j-1] <= arr[j] )
//                    break;
//                int temp = arr[j];      //交换操作
//                arr[j] = arr[j-1];
//                arr[j-1] = temp;
//                System.out.println("Sorting:  " + Arrays.toString(arr));
//            }
//        }
		
	}
}
