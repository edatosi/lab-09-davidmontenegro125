package listas;

import java.util.NoSuchElementException;

import javax.swing.plaf.TreeUI;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 * Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test).
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 * java.util.LinkedList ni ArrayList dentro de esta clase.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        return cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        // 1. Crear nuevo nodo
        Nodo<T> nuevoNodo = new Nodo<T>(dato);
        // 2. Conectar nuevo nodo a la cabeza
        nuevoNodo.setSiguiente(this.cabeza);
        // 3. Volvemos el nuevo nodo como cabeza
        this.cabeza = nuevoNodo;
        // 4. Aumentar la cantidad
        cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
        if (estaVacia()) {
            agregarAlInicio(dato);
        } else {
            // 1. Crear nuevo nodo
            Nodo<T> nuevoNodo = new Nodo<T>(dato);
            // 2. Obtener el nodo que está a la cabeza
            Nodo<T> nodoActual = this.cabeza;
            // 3. Buscar el último nodo
            while (nodoActual.getSiguiente() != null) {
                nodoActual = nodoActual.getSiguiente();
            }
            // 4. Enlazar el nuevo nodo con el último
            nodoActual.setSiguiente(nuevoNodo);
            // 5. Aumentar cantidad
            cantidad++;
        }

    }

    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> nodoActual = this.cabeza;
        while (nodoActual.getSiguiente() != null) {
            nodoActual = nodoActual.getSiguiente();
        }
        return nodoActual.getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("El índice ingresado está por fuera de la lista.");
        }
        Nodo<T> nodoActual = cabeza;
        for (int i = 0; i < indice; i++) {
            nodoActual = nodoActual.getSiguiente();
        }
        return nodoActual.getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        return indiceDe(dato) != -1;
    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        Nodo<T> nodoActual = cabeza;
        for (int i = 0; i < cantidad; i++) {
            if (nodoActual.getDato() == dato || (dato != null && dato.equals(nodoActual.getDato()))) {
                return i;
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return -1;

    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
        if (indice < 0 || indice > tamanio()) {
            throw new IndexOutOfBoundsException("El índice ingresado está por fuera de la lista.");
        }
        if (indice == 0){
            agregarAlInicio(dato);
        } else {
            Nodo<T> nuevoNodo = new Nodo<T>(dato);
            Nodo<T> nodoActual = cabeza;
            for (int i=0; i < indice -1; i++){
                nodoActual = nodoActual.getSiguiente();
            }
            nuevoNodo.setSiguiente(nodoActual.getSiguiente());
            nodoActual.setSiguiente(nuevoNodo);
            cantidad++;
        }
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("El índice ingresado está por fuera de la lista.");
        }
        Nodo<T> nodoActual = cabeza;
        for (int i=0; i < indice; i++){
            nodoActual = nodoActual.getSiguiente();
        }
        Nodo<T> nodoAnterior = nodoActual;
        T datoAnterior = nodoAnterior.getDato();
        nodoActual.setDato(dato);
        return datoAnterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * 
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        Nodo<T> nodoActual = cabeza;
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        T datoEliminado = nodoActual.getDato();
        cabeza = nodoActual.getSiguiente();
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina y retorna el último elemento.
     * 
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        // Atención al caso de un solo elemento.
        Nodo<T> nodoActual = cabeza;
        if (nodoActual == null || estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        if (nodoActual.getSiguiente() == null) {
            T datoEliminado = nodoActual.getDato();
            cabeza = null;
            cantidad--;
            return datoEliminado;
        }
        while (nodoActual.getSiguiente().getSiguiente() != null) {
            nodoActual = nodoActual.getSiguiente();
        }
        T datoEliminado = nodoActual.getSiguiente().getDato();
        nodoActual.setSiguiente(null);
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * 
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("El índice ingresado está por fuera de la lista.");
        }
        if (indice == 0) {
            return eliminarPrimero();
        } else if (indice == cantidad - 1) {
            return eliminarUltimo();
        } else {
            Nodo<T> nodoActual = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                nodoActual = nodoActual.getSiguiente();
            }
            T datoEliminado = nodoActual.getSiguiente().getDato();
            nodoActual.setSiguiente(nodoActual.getSiguiente().getSiguiente());
            cantidad--;
            return datoEliminado;
        }
    }

    /**
     * Elimina la primera aparición del dato.
     * 
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        int indice = indiceDe(dato);
        if (indice != -1) {
            eliminar(indice);
            return true;
        }
        return false;
    }

    /** Deja la lista vacía. */
    public void vaciar() {
        cabeza = null;
        cantidad = 0;
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Nodo<T> nodoActual = cabeza;
        while (nodoActual != null) {
            sb.append(nodoActual.getDato());
            if (nodoActual.getSiguiente() != null) {
                sb.append(", ");
            }
            nodoActual = nodoActual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }
        cabeza = anterior;
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        // TODO Etapa 8: escriba su código aquí
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;
        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        Nodo<T> nodoActual = cabeza;
        while (nodoActual != null) {
            Nodo<T> nodoSiguiente = nodoActual.getSiguiente();
            Nodo<T> nodoAnterior = nodoActual;
            while (nodoSiguiente != null) {
                if (nodoActual.getDato() == nodoSiguiente.getDato() || (nodoActual.getDato() != null && nodoActual.getDato().equals(nodoSiguiente.getDato()))) {
                    nodoAnterior.setSiguiente(nodoSiguiente.getSiguiente());
                    cantidad--;
                } else {
                    nodoAnterior = nodoSiguiente;
                }
                nodoSiguiente = nodoSiguiente.getSiguiente();
            }
            nodoActual = nodoActual.getSiguiente();
        }
    }
}