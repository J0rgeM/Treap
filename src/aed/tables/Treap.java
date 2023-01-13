package aed.tables;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Treap<Key extends Comparable<Key>,Value> {

    //if you want to use a different organization than a set of nodes with pointers, you can do it, but you will have to change
    //the implementation of the getHeapArray method, and eventually the printing method (so that is prints a "node" in the same way)
    private class Node {
        private Key key;
        private int priority;
        private Value value;
        private Node left;
        private Node right;
        private int size;

        //you can add additional properties and methods, as long as you maintain this constructor and the toString method with the same semantic
        public Node(Key k, Value v, int size, int priority)
        {
            this.key = k;
            this.value = v;
            this.size = size;
            this.priority = priority;
        }

        /*
        private Node rotateLeft()
        {
            // rotacao de uma ligacao da direita para a esquerda
            Node current = this.right;
            this.right = current.left;
            current.left = this;

            this.size = size(this.right) + size(this.left) + 1;
            return current;
        }

        private Node rotateRight()
        {
            // rotacao de uma ligacao da esquerda para a direita
            Node current = this.left;
            this.left = current.right;
            current.right = this;

            this.size = size(this.right) + size(this.left) + 1;
            return current;
        }*/

        public String toString()
        {
            return "[k:" + this.key + " v:" + this.value + " p:" + this.priority + " s:" + this.size + "]";
        }

        //if needed, you can add additional methods to the private class Node
    }

    private Node rotateLeft(Node n)
    {
        // rotacao de uma ligacao da direita para a esquerda
        Node current = n.right;
        n.right = current.left;
        current.left = n;

        //atualiza o tamanho
        n.size = size(n.right) + size(n.left) + 1; //tamanho do pai é soma do tamanho dos filhos mais ele proprio
        return current;
    }

    private Node rotateRight(Node n)
    {
        // rotacao de uma ligacao da esquerda para a direita
        Node current = n.left;
        n.left = current.right;
        current.right = n;

        //atualiza o tamanho
        n.size = size(n.right) + size(n.left) + 1; //tamanho do pai é soma do tamanho dos filhos mais ele proprio
        return current;
    }

    private Random generator;
    private Node root = null;

    public Treap()
    {
        this.generator = new Random();
        this.root = null;
    }

    public Treap(Random r)
    {
        this.generator = r;
        this.root = null;
    }

    /** Retorna o numero de pares de valores-chave nesta tabela de símbolos.*/
    public int size()
    {
        return size(this.root);
    }

    private int size(Node current)
    {
        if (current == null)
            return 0;
        else
            return current.size;
    }

    /** Verifica se existe a chave dada na Treap.
        Retorna true caso exista chave e false caso contrario.*/
    public boolean containsKey(Key k)
    {
        Node node = root;
        while (node != null)
        {
            int cmp = k.compareTo(node.key); // compara a key pedida com a key do node recebido
            //cmp < 0 quando key for menor que o x.key
            if (cmp < 0)
                node = node.left; //anda para a esquerda
            else if (cmp > 0)
                node = node.right; //anda para a diur
            else
                return true;
        }
        return false;
    }

    /** Retorna o valor guardado na tabela de simbolos para a chave recebida como argumento.
        Caso a chave nao existe na tabela e retornado nulo.*/
    public Value get(Key k)
    {
        return get(this.root, k);
    }

    private Value get(Node n, Key k)
    {
        if (n == null) return null;

        int cmp = k.compareTo(n.key); //compara a key pedida com a key do node recebido
        //cmp < 0 quando key for menor que o x.key
        if (cmp < 0)
            return get(n.left, k); //anda para a esquerda
        else if (cmp > 0)
            return get(n.right, k); //anda para a direita
        else //cmp = 0
            return n.value;
    }

    /** Insere o valor v no Treap associando-o a chave k.
        Caso a chave ja exista na tabela é feita uma atualizacao ao seu valor.
        Inserir o valor null numa tabela de símbolos deve ser equivalente a fazer delete(k).*/
    public void put(Key k, Value v)
    {
        this.root = put(this.root, k, v);
    }

    private Node put(Node n, Key k, Value v)
    {
        if (n == null) return new Node(k, v, 1, generator.nextInt());

        int cmp = k.compareTo(n.key); //compara a key pedida com a key do node recebido
        //cmp < 0 quando key for menor que o x.key
        if (cmp < 0) // colocar a esquerda
        {
            n.left = put(n.left, k, v); //avanco o node para o seguinte
            if (n.left.priority > n.priority) // esta ver se a priority da esquerda maior que a normal
                // n = n.rotateRight();
                n = rotateRight(n);
        } else if (cmp > 0) // colocar a direita
        {
            n.right = put(n.right, k, v);
            if (n.right.priority > n.priority)
                // n = n.rotateLeft();
                n = rotateLeft(n);
        } else
            n.value = v; // altera o valor guardado no para v
        //atualiza o tamanho
        n.size = size(n.left) + size(n.right) + 1; //tamanho do pai é soma do tamanho dos filhos mais ele proprio
        return n;
    }

    /** Remove a chave (e o seu valor) da arvore.
        Tentar remover uma chave que nao existe nao produz qualquer efeito.*/
    public void delete(Key key)
    {
        //TODO: implements
    }

    /** Devolve um array com dois Treaps.
        Resulta de uma separacao do Treap original em dois usando a chave recebida.*/
    @SuppressWarnings("rawtypes")
    public Treap[] split(Key k)
    {
        //TODO: implements
        return null;
    }

    /** Retorna nulo se estiver vazio.*/
    public boolean isEmpty( )
    {
        return root == null;
    }

    /** Encontra o menor item da Treap, retorna o menor item ou retorna nulo se estiver vazio.*/
    public Key min()
    {
        if(isEmpty())
            return null;

        Node pointer = root;
        while(pointer.left != null)
            pointer = pointer.left; //anda para a esquerda

        return pointer.key;
    }

    /** Encontra o maior item da Treap, retorna o maior item ou retorna nulo se estiver vazio.*/
    public Key max()
    {
        if(isEmpty())
            return null;

        Node pointer = root;
        while(pointer.right != null)
            pointer = pointer.right; //anda para a direita

        return pointer.key;
    }

    /** Remove a menor chave e o valor associado da Treap.*/
    public void deleteMin()
    {
        if (!isEmpty())
            root = deleteMin(root);
    }

    private Node deleteMin(Node current)
    {
        if (current.left == null)
            return current.right; //nova ligacao para o elemento à direita do minimo

        current.left = deleteMin(current.left);
        //atualiza o size do node
        current.size = size(current.left) + size(current.right) + 1; //tamanho do pai é soma do tamanho dos filhos mais ele proprio
        return current;
    }

    /** Remove a maior chave e o valor associado da Treap.*/
    public void deleteMax()
    {
        if (!isEmpty())
            root = deleteMax(root);
    }

    private Node deleteMax(Node current)
    {
        if (current.right == null)
            return current.left; //nova ligacao para o elemento à maximo do minimo

        current.right = deleteMax(current.right);
        //atualiza o size do node
        current.size = size(current.left) + size(current.right) + 1; //tamanho do pai é soma do tamanho dos filhos mais ele proprio
        return current;
    }

    /** Retorna o número de chaves da Treap estritamente menor que a chave.*/
    public int rank(Key k)
    {
        if (k == null)
            return 0;
        return rank(k, root);
    }

    private int rank(Key key, Node x)
    {
        if (x == null)
            return 0;

        //cmp < 0 quando key for menor que o x.key
        int cmp = key.compareTo(x.key); // compara a key pedida com a key do node recebido
        if(cmp < 0)
            return rank(key, x.left); // anda para a esquerda
            //cmp > 0 quando key for maior que o x.key
        else if(cmp > 0)
            // soma o size a esquerda de x + o proprio, e verifica o que falta somar à direita do node
            return size(x.left) + 1 + rank(key, x.right);
        else // cmp = 0 quando key igual a x.key
            return size(x.left);
    }

    /** Retorna o número de chaves na Treap no intervalo fornecido.*/
    public int size(Key min, Key max)
    {
        if (min == null)
            return 0;
        if (max == null)
            return 0;

        // compara a key pedida com a key do node recebido
        int cmp = min.compareTo(max);
        if (cmp > 0)
            return 0;

        if (containsKey(max)) // se chave existir, faz se a subtracao +1 senao apenas faz a subtracao para dar o tamanho
            return rank(max) - rank(min) + 1;//retorna o numero de chaves na Treap entre o max e o min +1
        else
            return rank(max) - rank(min);//retorna o numero de chaves na Treap entre o max e o min
    }

    /** Retorna a n-esima menor chave na Treap. Caso nao exista retorna nulo*/
    public Key select(int n)
    {
        if(n < 0) return null;

        return select(root, n);
    }

    private Key select(Node current, int n)
    {
        if (current == null) return null;

        if(size(current.left) > n) // size do lado esquerdo > n
            return select(current.left,  n); // anda para a esquerda
        else if(size(current.left) < n) // size do lado esquerdo < n
            return select(current.right, n - size(current.left) - 1); // anda para a direita e subtrai pelo tamanho do atual e -1
        else // atingiu o node pretendido
            return current.key;
    }

    public Iterable<Key> keys()
    {
        Queue<Key> queue = new LinkedList<Key>();
        keys(root,queue);
        return queue;
    }

    public void keys(Node current, Queue<Key> queue)
    {
        if (current == null) return;

        if (current.left != null)
            keys(current.left,queue); //anda para a esquerda

        queue.add(current.key); // add uma key no final

        if (current.right != null)
            keys(current.right,queue); //anda para a direita
    }

    public Iterable<Value> values()
    {
        Queue <Value> queue = new LinkedList<Value>();
        values(root,queue);
        return queue;
    }

    public void values(Node current, Queue<Value> queue)
    {
        if (current == null) return;

        if (current.left != null)
            values(current.left,queue); //anda para a esquerda

        queue.add(current.value); // add uma key no final

        if (current.right != null)
            values(current.right,queue); //anda para a direita
    }

    public Iterable<Integer> priorities()
    {
        Queue <Integer> queue = new LinkedList<Integer>();
        priorities(root,queue);
        return queue;
    }

    public void priorities(Node current, Queue<Integer> queue)
    {
        if (current == null) return;

        if (current.left != null)
            priorities(current.left,queue); //anda para a esquerda

        queue.add(current.priority); // add uma key no final

        if (current.right != null)
            priorities(current.right,queue); //anda para a direita
    }

    public Iterable<Key> keys(Key min, Key max)
    {
        Queue<Key> queue = new LinkedList<Key>();

        if (min == null) return null;
        if (max == null) return null;

        keys(root, queue, min, max);
        return queue;
    }

    private void keys(Node current, Queue<Key> queue, Key min, Key max)
    {
        if (current == null) return;

        int cmpMin = min.compareTo(current.key);
        int cmpMax = max.compareTo(current.key);

        if (cmpMin < 0)
            keys(current.left, queue, min, max); // anda para a esquerda

        if (cmpMin <= 0 && cmpMax >= 0)
            queue.add(current.key); // add chave

        if (cmpMax > 0)
            keys(current.right, queue, min, max); // anda para a direita
    }

    public Treap<Key,Value> shallowCopy()
    {
        Treap<Key,Value> treap = new Treap<>();
        treap.root = shallowCopy(root);
        return treap;
    }

    public Node shallowCopy(Node current)
    {
        if (current == null) return null;

        Node copy = new Node(current.key, current.value, current.size, current.priority); // cria node atraves do construtor

        if (current.left != null)
           copy.left = shallowCopy(current.left); //copia para o node copy e anda para a esquerda

        if (current.right != null)
            copy.right = shallowCopy(current.right); //copia para o node copy e anda para a direita

        return copy;
    }

    //helper method that uses the treap to build an array with a heap structure
    public void fillHeapArray(Node n, Object[] a, int position)
    {
        if(n == null) return;

        if(position < a.length)
        {
            a[position] = n;
            fillHeapArray(n.left,a,2*position);
            fillHeapArray(n.right,a,2*position+1);
        }
    }

    //if you want to use a different organization that a set of nodes with pointers, you can do it, but you will have to change
    //this method to be consistent with your implementation
    public Object[] getHeapArray()
    {
        //we only store the first 31 elements (position 0 is not used, so we need a size of 32), to print the first 5 rows of the treap
        Object[] a = new Object[32];
        fillHeapArray(this.root,a,1);
        return a;
    }

    public void printCentered(String line)
    {
        //assuming 120 characters width for a line
        int padding = (120 - line.length())/2;
        if(padding < 0) padding = 0;
        String paddingString = "";
        for(int i = 0; i < padding; i++)
        {
            paddingString +=" ";
        }

        System.out.println(paddingString + line);
    }

    //this is used by some of the automatic tests in Mooshak. It is used to print the first 5 levels of a Treap.
    //feel free to use it for your own tests
   public void printTreapBeginning() {
        Object[] heap = getHeapArray();
        int size = size(this.root);
        int printedNodes = 0;
        String nodeToPrint;
        int i = 1;
        int nodes;
        String line;

        //only prints the first five levels
        for (int depth = 0; depth < 5; depth++) {
            //number of nodes to print at a particular depth
            nodes = (int) Math.pow(2, depth);
            line = "";
            for (int j = 0; j < nodes && i < heap.length; j++) {
                if (heap[i] != null) {
                    nodeToPrint = heap[i].toString();
                    printedNodes++;
                } else {
                    nodeToPrint = "[null]";
                }
                line += " " + nodeToPrint;
                i++;
            }

            printCentered(line);
            if (i >= heap.length || printedNodes >= size) break;

        }
    }

    public static class Stopwatch
    {
        private final long start;
        public Stopwatch()
        {
            start = System.currentTimeMillis();
        }
        public double elapsedTime()
        {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }
    }

    public static void main(String[] args)
    {
        /*
        Random r = new Random(5000);
        Treap tree = new Treap(r);

        for (int i = 0; i < 65536; i++) // Random
        {
            var oldClock = new Stopwatch();
            tree.put(r.nextInt(), r.nextInt());
        }

        for (int i = 0; i < 65536; i++) // Ordem Crescente
        {
            var oldClock = new Stopwatch();
            tree.put(i, i);
        }

        { //Teste empirico 1 - put
            var oldClock = new Stopwatch(); // relogio
            for (int i = 1; i <= 100000; i++)
                tree.put(r.nextInt(),r.nextInt());

            double oldTime = oldClock.elapsedTime();
            oldTime /= 100000;
            System.out.println("Random put:: "+ String.format("%.16f", oldTime));

        }

        { //Teste empirico 2 - put
            var oldClock = new Stopwatch(); // relogio
            for (int i = 1; i <= 100000; i++)
                tree.put(i,i);

            double oldTime = oldClock.elapsedTime();
            oldTime /= 100000;
            System.out.println("Ordem Crescente put: "+ String.format("%.16f", oldTime));
        }

        { //Teste empirico 1 - get
            var oldClock = new Stopwatch(); // relogio
            for (int i = 1; i <= 100000; i++)
                tree.get(r.nextInt());

            double oldTime = oldClock.elapsedTime();
            oldTime /= 100000;
            System.out.println("Random get: "+ String.format("%.16f", oldTime));

        }
        { //Teste empirico 2 - get
            var oldClock = new Stopwatch(); // relogio
            for (int i = 1; i <= 100000; i++)
                tree.get(i);

            double oldTime = oldClock.elapsedTime();
            oldTime /= 100000;
            System.out.println("Ordem Crescente get: "+ String.format("%.16f", oldTime));
        }
          Random put: 0.0000018800000000
            Ordem Crescente put: 0.0000004800000000
            Random get: 0.0000005200000000
            Ordem Crescente get: 0.0000001400000000
         */
    }
}

