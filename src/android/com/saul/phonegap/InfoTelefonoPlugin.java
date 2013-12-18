package com.saul.phonegap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

public class InfoTelefonoPlugin extends CordovaPlugin {

	public static final String OBTENER_INFO_ACCION = "OBTENER_INFO_ACCION"; // En el parametro del exec 
	
	@Override
	public boolean execute(String action, JSONArray args,
						   CallbackContext ctx) throws JSONException {
		boolean resultado = false;
		try {
			// Acciones del plugin, desde el exec del javascript que pasen
			if (OBTENER_INFO_ACCION.equalsIgnoreCase(action)) {					
				//long param = args.getLong(0) // Argumentos que podria haber				
				// Devolvemos los datos al callback con JSON que le gustan mas				
				JSONObject jsonSuccess = this.obtenerInfoTelefonoImpl();				
				//ctx.success(jsonSuccess) // Para decir ha ido bien y pasale esto como parametro si ha ido bien. La forma rara:
				// enviar resultado a javascript
				ctx.sendPluginResult(
						new PluginResult(PluginResult.Status.OK, jsonSuccess)						
				);							
			} else {
				throw new IllegalArgumentException(action + " no soportada");
			}			
			resultado = true;
		} catch (Throwable exc) { // Es la superclase de exception, capturando cualquier cosa
			JSONObject jsonError = new JSONObject("{ \"mensaje\" : \"" + exc.getMessage() + "\"}");
			ctx.sendPluginResult(
					new PluginResult(PluginResult.Status.ERROR, jsonError)						
			);
		}
		return resultado;		
	}

	private JSONObject obtenerInfoTelefonoImpl() throws JSONException {		
		// Coger el activity de la aplicacion, pedirle un servicio de gestion del telefono y preguntarle por el IMEI y esas cosas.
		// apunta al cordova activity, es una pantalla, un objeto
		TelephonyManager manager = (TelephonyManager)super.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE); // referencia al objeto
		String numero = manager.getLine1Number();
		String imei = manager.getDeviceId();
		String imsi = manager.getSubscriberId(); // Numero unico del telefono a nivel mundial, marca cada pais con su prefijo y cada compañia otro prefijo y un numero unico
		// Debe tardar muy poco, sino habria otra forma de montarlo
		
		// Devolver objeto JSON MessageFormat para formatear cadnas de caracteres, pero si hay "" hay problemas. Lo hace con ReplaceAll
		String jsonString = "{ 'numero' : '{0}', 'imei' : '{1}', 'imsi' : '{2}'}";
		// Reemplaza las ' por " y los {numero} por el parametro
		jsonString = jsonString.replaceAll("'", "\"").replace("{0}", numero == null ? "" : numero)
													 .replace("{1}", imei == null ? "" : imei)
													 .replace("{2}", imsi == null ? "" : imsi); // Replace pq el primero sobre todos y luego solo el preplace comcreto
																								// Evitamos si es nulo, para que saque comillas
		// Convertir a JSONObject
		return new JSONObject(jsonString);				
	}
	
	
}
