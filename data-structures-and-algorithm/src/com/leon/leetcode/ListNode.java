package com.leon.leetcode;

/**
 * Definition for singly-linked list.
 * @package: com.leon.leetcode.L203
 * @author: 陈明磊<chenminglei@163.com>
 * @date: 2018/8/20 19:13
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class ListNode {

    int val;
    ListNode next;
    ListNode(int x) { val = x; }

    /**
     * 构造函数
     * 数组转链表
     * @param arr
     */
    public ListNode(int[] arr){
        if(arr == null || arr.length == 0){
            try {
                throw new IllegalAccessException("arr can not be empoty!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.val = arr[0];
        ListNode cur = this;
        for (int i = 0; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }

    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        ListNode cur = this;
        while(cur != null){
         res.append(cur.val + "->");
         cur = cur.next;
        }

        res.append("NULL");
        return res.toString();
    }


}
