
// Este modulo invocara eal exec, la parte nativa
cordova.define("com.saul.phonegap.infotelefonoplugin", function(require, exports, module) {
	
	var exec = require('cordova/exec'); // llamada al api de android
	var InfoTelefono = require ('./InfoTelefono'); // coger el componente del namespace, otro plugin: require('org.apache.cordova.Contacts') Hay que añadirlos con el plugin add
	// Otra forma: require('com.saul.phonegap.InfoTelefono')
	
	var InfoTelefonoPlugin = function() {} // constructor
	
	InfoTelefonoPlugin.prototype.obtenerInfo = function(success, fail) { // Es lo que invocara el usuario: window.telefono.obetenerInfo(..)
		//exec(success, fail, 'InfoTelefonoPlugin', []); // Esta en un fichero de configuracion el nombre InfoTelefonoPlugin como referencia, no es el nombre de la clase y params de llamada args como no hay lo ponemos vacio.
		// Invoca al execute internamente de la clase java. Se puede sustituir success por tu propia funcion => function(jsonJava) {var resultado = new InfoTelefono(); resultado.imei = jsonJava.imei; resultado.numero = jsonJava.numero; resultado.imsi = jsonJava.imsi; success(resultado); }
		// Asi devuelves un objeto definido por ti con su documentacion  y no el java directo.
		exec(function(jsonJava) {
				var resultado = new InfoTelefono(); // del namespace del js
				resultado.imei = jsonJava.imei; 
				resultado.numero = jsonJava.numero; 
				resultado.imsi = jsonJava.imsi; 
				success(resultado); 
		}, fail, 'InfoTelefonoPlugin', 'OBTENER_INFO_ACCION', []);
	}
	
	module.exports = new InfoTelefonoPlugin(); // Devolvemos el tipo del objeto
	
}); // Usuario: window.telefono.obtenerInfo(function(infoTelefono) {...}). // Success lo hace cordova el exec