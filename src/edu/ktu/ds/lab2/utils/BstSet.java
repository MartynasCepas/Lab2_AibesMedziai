package edu.ktu.ds.lab2.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import edu.ktu.ds.lab2.cepas.Item;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E> interfeisą
 *            tenkinantis objektas.
 * 
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class BstSet<E extends Comparable<E>> implements SortedSet<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<E>
     */
    public BstSet() {
        this.c = Comparator.naturalOrder();
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<E>
     *
     * @param c Komparatorius
     */
    public BstSet(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }
    
    /**
     *
     * @param c
     * @return
     */
    public boolean addAll(BstSet<? extends E> c) {
        boolean end = false;
        for (E element : c) {
            add(element);
            end = true;
        }
        return end;
    }
    
   public E higher (E e)
    {
        if(root==null) return null;
        BstNode<E> checkable = root;
        E saved = null;
        while(checkable!=null)
        {
            if(checkable.element.compareTo(e) == -1 || checkable.element.compareTo(e) == 0)
            {
                if(checkable.right != null)
                {
                    checkable = checkable.right;
                    continue;
                }
                return saved;
            }
            else
            {
                saved = checkable.element;
                if(checkable.left!=null)
                {
                    checkable = checkable.left;
                    continue;
                }
                return saved;
            }
        }
        return saved;
    }
    
    public E pollLast() {
        BstNode<E> rootTemp = root;
        E max = last();
        E temp = null;
        while (rootTemp != null) {
            if (rootTemp.right == null) {
                return null;
            }
            temp = rootTemp.right.element;
            if (temp == max) {
                rootTemp.right = null;
                return temp;
            }
            rootTemp = rootTemp.right;
        }
        return null;
    }
    
    public E last() {

        E element = null;
        BstNode<E> n = root;

        while (n != null) {
            element = n.element;
            n = (true) ? n.right : n.left;
        }
        return element;
    }
    
    public E pollFirst() {
        E ats = getMin(root).element;
        remove(ats);

        return ats;
    }
    
    public E floor(E element){       
              
        if(element ==null)
            return null;
        BstNode<E> x = floor(root,element);
        if (x == null) return null;
        else return x.element;
    }
    
    public BstNode<E> floor(BstNode<E> x, E element){
        if(x==null)
            return null;
        int cmp = element.compareTo(x.element);
        if(cmp == 0)return x;
        if(cmp < 0)return floor(x.left, element);
        BstNode<E> t = floor(x.right, element);
        if(t!=null)return t;
        else return x;
        
    }
    
    public E lower(E e)
    {
        if(root==null) return null;
        BstNode<E> checkable = root;
        E saved = null;
        while(checkable!=null)
        {
            if(checkable.element.compareTo(e) == 1 || checkable.element.compareTo(e) == 0)
            {
                if(checkable.left!=null)
                {
                    checkable = checkable.left;
                    continue;
                }
                return saved;
             }
             else
             {
                saved = checkable.element;
                if(checkable.right!=null)
                {
                    checkable = checkable.right;
                    continue;
                }
                return saved;
             }
        }
        return saved;
    }
    
     public Set<E> headSet(E element, boolean inclusive) {
        Set<E> set = new BstSet<E>(Item.byYearAndPrice2);
       
        //be iteratoriaus
        if(inclusive && this.contains(element))
        {
            set.add(element);
        }
        //bet kokiu atveju
        recursiveAdd(-1, set, root, element);
        
        //su iteratorium
        /*for(Object o : this)
        {
            if(((E) o).compareTo(element) == -1)
            {
                set.add((E) o);
            }
        }*/
        return set;
    }
        public Set<E> headSet(E element) {
        Set<E> set = new BstSet<E>(Item.byPrice);
       
        //be iteratoriaus
        recursiveAdd(-1, set, root, element);
        
        //su iteratorium
        /*for(Object o : this)
        {
            if(((E) o).compareTo(element) == -1)
            {
                set.add((E) o);
            }
        }*/
        return set;
    }
    
    @Override
    public Set<E> tailSet(E element) {
        Set<E> set = new BstSet<E>(Item.byPrice);
       
        //be iteratoriaus
        recursiveAdd(1, set, root, element);
        
        if(this.contains(element)) set.add(element);
        
        return set;
    }
     
     public void recursiveAdd(int comparatorParam, Set<E> set, BstNode<E> newParent, E toCompare)
    {
        //if comparatorParam=1, check newParent > toCompare
        //if comparatorParam=-1 check newParent < toCompare
        int cmp = newParent.element.compareTo(toCompare); 
        if(cmp == comparatorParam)
        {
            set.add(newParent.element);
            //check for childs and add them accordingly to the same criteria
            if(newParent.left != null)
            {
                recursiveAdd(comparatorParam, set, newParent.left, toCompare);
            }
            if(newParent.right != null)
            {
                recursiveAdd(comparatorParam, set, newParent.right, toCompare);
            }
        }
        //even if it didn't satisfy the comparing param, there MIGHT be something left
        else
        {
            //if we're checking for newParent > toCompare, there might be a right element that would satisfy this:
            //newParent.right > toCompare
            if(comparatorParam == 1 && newParent.right != null)
            {
                recursiveAdd(comparatorParam, set, newParent.right, toCompare);
            }
            //conversely there might be a left element which if we're checking for newParent < toCompare, satisfies:
            //newParent.left < toCompare
            else if(comparatorParam == -1 && newParent.left != null)
            {
                recursiveAdd(comparatorParam, set, newParent.left, toCompare);
            }
        }
    }
    
    @Override
    public SortedSet<E> subSet(E element1, E element2) {
        BstSet<E> set = new BstSet();
        Iterator var3 = this.iterator();
        while (var3.hasNext()) {
            E item = (E) var3.next();
            
            if (item.compareTo(element1) >= 0 && item.compareTo(element2) != 0){
                set.add(item);
            }
            else if(item.compareTo(element2) == 0){
                return set;
            }
        }
        return set;
    }
    
    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }
    
     public int findHeight()
    {
        return findHeightRecursive(root);
    }

    private int findHeightRecursive(BstNode<E> node) {
        BstNode<E> n = node;
        if (root == null) {
            return 0;
        }
        int hLeft = 0;
        int hRight = 0;

        if (n.left != null) {
            hLeft = findHeightRecursive(n.left);
        }

        if (n.right != null) {
            hRight = findHeightRecursive(n.right);
        }

        int max = (hLeft > hRight) ? hLeft : hRight;

        return max + 1;
    }

    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    @Override
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSet<E> cl = (BstSet<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */


    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorBst(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorBst(false);
    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorBst implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;

        IteratorBst(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                BstNode<E> n = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode<E> node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Studentams reikia realizuoti remove()");
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
