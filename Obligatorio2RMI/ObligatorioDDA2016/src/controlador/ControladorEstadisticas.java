/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.IModelo;
import modelo.Jugador;
import modelo.Modelo;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;

/**
 *
 * @author Euge
 */
public class ControladorEstadisticas extends UnicastRemoteObject implements ObservadorRemoto{
    public IModelo modelo;
    public VistaEstadisticas vista;
    public Jugador jugador;

    public ControladorEstadisticas(VistaEstadisticas vista, Jugador jugador) throws RemoteException{
        try {
            this.vista = vista;
            this.modelo = (Modelo)Naming.lookup("rmi://localhost/modelo");
            this.jugador = jugador;
            modelo.agregar(this);
        } catch (NotBoundException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTotalApostadoTodos(){
        vista.mostrarTodosApostado(modelo.totalApostadoTodos());
    }

    public void mostrarTotalCobradoTodos() {
        vista.mostrarTodosCobrado(modelo.totalCobradoTodos());
    }

    public void mostrarTotalApostado() {
        vista.mostrarTotalApostado(jugador.getTotalApostado());
    }

    public void mostrarTotalCobrado() {
        vista.mostrarTotalCobrado(jugador.getTotalCobrado());
    }

    public void habilitarStats(boolean habilitar) {
        jugador.setStatsOn(!habilitar);
    }

//    public void eliminarObservador() {
//        modelo.deleteObserver(this);
//    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if(param.equals(Modelo.EVENTO_ACTUALIZA_SALDOS)){
            vista.mostrarTodosApostado(modelo.totalApostadoTodos());
            vista.mostrarTodosCobrado(modelo.totalCobradoTodos());
            vista.mostrarTotalApostado(jugador.getTotalApostado());
            vista.mostrarTotalCobrado(jugador.getTotalCobrado());
        }
    }
}