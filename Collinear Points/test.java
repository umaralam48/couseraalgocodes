class Cat extends Animal{
    int a=11;
    void eat(){
        System.out.println("Cat eating");
    }
    
    public static void main(String[] args){
        Cat obj=new Cat();
        Animal obj1=new Animal();
        obj1.eat();
        super.eat();
        System.out.println(super.a);
    }
}

class Animal{
   static int a=9;
     void eat(){
        System.out.println("animal eating");
    }
}

