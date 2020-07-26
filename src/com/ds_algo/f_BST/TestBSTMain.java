package com.ds_algo.f_BST;

import com.tool.binaryTree.printer.BinaryTreeInfo;
import com.tool.binaryTree.printer.BinaryTrees;
import com.tool.common.file.Files;
import java.util.Comparator;

@SuppressWarnings({"unused", "unchecked"})
public class TestBSTMain {
    public static void main(String[] args) {
//        test();
        test7();
    }

    private static class PersonComparator implements Comparator<Person> {
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }

    private static class PersonComparator2 implements Comparator<Person> {
        public int compare(Person e1, Person e2) {
            return e2.getAge() - e1.getAge();
        }
    }


    public static void test(){
        BST bst = new BST();
        int[] pool = new int[]{7,8,3,1,5,4,9,6};
        for (int i = 0; i < pool.length; i++) {
            bst.add(pool[i]);
        }
        BinaryTrees.println(bst);

        System.out.println("tree height: " + bst.height());
        System.out.println("tree is Completed: " + bst.isCompletedBinaryTree());

        BinaryTree.Visitor visitor = new BinaryTree.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                if (element == 4){
                    return true;
                }
                System.out.print(element + " ");
                return false;
            }
        };

        bst.inorderTravel(visitor);
        System.out.println();
        bst.preorderTravel(visitor);
        System.out.println();
        bst.postOrderTravel(visitor);
    }

    static void test1() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
    }

    static void test2() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BST<Person> bst1 = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst1.add(new Person(data[i]));
        }

        BinaryTrees.println(bst1);

        BST<Person> bst2 = new BST<>(new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });
        for (int i = 0; i < data.length; i++) {
            bst2.add(new Person(data[i]));
        }
        BinaryTrees.println(bst2);
    }

    static void test3() {
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < 40; i++) {
            bst.add((int)(Math.random() * 100));
        }

        String str = BinaryTrees.printString(bst);
        str += "\n";
        Files.writeToFile("/Users/tiny/Desktop/bst.txt", str, true);

        BinaryTrees.println(bst);
        System.out.println("tree height: " + bst.height());
        System.out.println("tree is Completed: " + bst.isCompletedBinaryTree());

        bst.inorderTravel(new BinaryTree.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                System.out.print(element + "_");
                return false;
            }
        });
    }

    static void test4() {
        BinaryTrees.println(new BinaryTreeInfo() {

            @Override
            public Object string(Object node) {
                return node.toString() + "_";
            }

            @Override
            public Object root() {
                return "A";
            }

            @Override
            public Object right(Object node) {
                if (node.equals("A")) return "C";
                if (node.equals("C")) return "E";
                return null;
            }

            @Override
            public Object left(Object node) {
                if (node.equals("A")) return "B";
                if (node.equals("C")) return "D";
                return null;
            }
        });

        // test3();


        /*
         * Java的匿名类，类似于iOS中的Block、JS中的闭包（function）
         */

//		BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new Comparator<Person>() {
//			@Override
//			public int compare(Person o1, Person o2) {
//				return 0;
//			}
//		});
//		bst1.add(new Person(12));
//		bst1.add(new Person(15));
//
//		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator());
//		bst2.add(new Person(12));
//		bst2.add(new Person(15));
//


//		BinarySearchTree<Car> bst4 = new BinarySearchTree<>(new Car);
//
//
//		java.util.Comparator<Integer> iComparator;
    }

    static void test5() {
        BST<Person> bst = new BST<>();
        bst.add(new Person(10, "jack"));
        bst.add(new Person(12, "rose"));
        bst.add(new Person(6, "jim"));
        bst.add(new Person(10, "michael"));
        BinaryTrees.println(bst);
    }

    static void test6() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

//		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//		for (int i = 0; i < 10; i++) {
//			bst.add((int)(Math.random() * 100));
//		}
        BinaryTrees.println(bst);
        System.out.println(bst.isCompletedBinaryTree());

		/*
		 *       7
		 *    4    9
		    2   5
		 */

		bst.levelOrderTravel(new BinaryTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print("_" + element + "_ ");
				return false;
			}
		});

        System.out.println();

		bst.inorderTravel(new BinaryTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print("_" + (element + 3) + "_ ");
                return false;
            }
		});

        System.out.println();
         System.out.println("bst height" + bst.height());
    }

    static void test7() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);

        bst.remove(7);

        BinaryTrees.println(bst);
    }

    static void test8() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
        System.out.println(bst.isCompletedBinaryTree());
    }

    static void test9() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);

        bst.preorderTravel(new BinaryTree.Visitor<Integer>() {
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 2 ? true : false;
            }
        });
        System.out.println();

        bst.inorderTravel(new BinaryTree.Visitor<Integer>() {
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 4 ? true : false;
            }
        });
        System.out.println();

        bst.postOrderTravel(new BinaryTree.Visitor<Integer>() {
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 4 ? true : false;
            }
        });
        System.out.println();

        bst.levelOrderTravel(new BinaryTree.Visitor<Integer>() {
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return element == 9 ? true : false;
            }
        });
        System.out.println();
    }

}
