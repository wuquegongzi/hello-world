package com.leon.leetcode.L203;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/description/
 *
 * 删除链表中等于给定值 val 的所有节点。
 *
 * 示例:
 *
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 *
 * @package: com.leon.leetcode.L203
 * @author: 陈明磊<swchenminglei@163.com>
 * @date: 2018/8/20 19:15
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
public class RemoveLinkedListElement {

    public ListNode removeElements(ListNode head, int val) {

        //首先删除 头节点为 val 的情况
        while(head != null && head.val == val){
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }

        //如果所有的头节点都为val的情况
        if(head == null){
           return null;
        }

        //删除链表中的val元素
        ListNode prev = head;
        while(prev.next != null){
            if(prev.next.val == val){
                ListNode delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;
            }else{
                prev = prev.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {

    }

}
