package com.leon.leetcode;

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
public class L203_RemoveLinkedListElement {



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

    /**
     * 使用虚拟头节点
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements2(ListNode head, int val) {


        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;

        ListNode prev = dummyHead;
        while(prev.next != null){
          if(prev.next.val == val){
             prev.next = prev.next.next;
          }else{
              prev = prev.next;
          }
        }

        return dummyHead.next;
    }

    /**
     *  递归
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements3(ListNode head, int val) {


        if(head == null){
            return null;
        }

        ListNode res = removeElements3(head.next,val);

        if(head.val == val){
           return res;
        }else{
            head.next = res;
            return head;
        }

    }

    /**
     * 递归精简
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements4(ListNode head, int val) {


        if(head == null){
            return null;
        }

        head.next = removeElements4(head.next,val);

        return head.val == val ? head.next : head ;

    }


    public static void main(String[] args) {

        int[] nums = {2,3,5,4,2,6,6,7,1,2,3,4,5,6,7,8,9,0,5,6,4,3,2,1};
        ListNode head = new ListNode(nums);

        System.out.println(head);

        L203_RemoveLinkedListElement removeLinkedListElement = new L203_RemoveLinkedListElement();
        ListNode res = removeLinkedListElement.removeElements(head,6);

        System.out.println(res);

        res = removeLinkedListElement.removeElements2(res,2);

        System.out.println(res);

        res = removeLinkedListElement.removeElements3(res,5);

        System.out.println(res);

        res = removeLinkedListElement.removeElements4(res,7);

        System.out.println(res);
    }

}
