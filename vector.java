public class vector<E> {

   int capacity=1; // Contains the increment value
   int elementCount;// Number of lements currently in vector stored in it
   Object elementData[]; // Array that holds the vector is stored in it
  

   vector()
   {
   elementData=new Object [capacity];
   }
    
   public void add (Object element)
   {int i=0;

    while(i<capacity)
    i++;
    if(i==capacity){
        elementData[i]=element;
    }
    capacity++;
    }

    public void remove(Object element)
    {
     int i=0;
     while(i!=capacity)
     {
         if(elementData[i]==element)
         {
          elementData[i]=null;
         }
         i++;
    
     }
    }
    public Object get(int index)
    {
        return elementData[index];
    }
    

   
}
