// Impedir impolucion del espacio de nombres, envolver todo con una funcion por ejemplo
// Crear modulos, nombre del modulo y funcion, y lo registra con el nombre que le damos 
cordova.define("com.saul.phonegap.InfoTelefono", function(require, exports, module) {	
	// registra esta funcion con ese nombre, pueden entrar params. module para ver que quieres que se registre.
	// Pasa objetos a la funcion
	var InfoTelefono = function (numero, imei, imsi) {
		this.numero = numero;
		this.imei = imei;
		this.imsi = imsi;
	}

	// Permite documentar a partir de /** --- */ y asi se genera documentacion de javascript
	InfoTelefono.prototype.numero = null;
	InfoTelefono.prototype.imei = null;
	InfoTelefono.prototype.imsi = null;	
	
	module.exports = InfoTelefono; // Cuando alguien pida por este modulo le devuelva este objeto, se registra con ese nombre y se queda con la variable que apunta al objeto funcion.
}); 
