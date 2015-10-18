
$(window).load(function(){

$('#login').walidate({
    submitSelector: 'input[type="submit"]', // Selektor des Absenden-Buttons
    validateClass: 'validate',              // Klasse die jedem zu validierenden Element hinzugefügt wird
    requiredClass: 'required',              // Klasse die jedem erforderlichen Element hinzugefügt wird
    validClass: 'valid',                    // Klasse die jedem gültigen Element hinzugefügt wird
    errorClass: 'error',
    doIfAllIsValid: function() {
        console.log('sent!');
		form.submit();
    },
    doIfSomethingIsInvalid: function() {
        console.log('error!');
    }
}, function() {                             // default: undefined;      (function)  runs after form initialisation
    // ...
});

$('#reset').click(function () {
    $('#form').walidate('reset', function(){
        console.log('my callback!');
    });
    return false;
});


$('#username').walidate('validate', {
                      // default: undefined;      (string)
    //required: false,                        // default: true;           (boolean)
    expression: /^[a-zA-Z]{3,}$/,           // default: undefined;      (Regex)
    eventHandler: 'blur keyup',             // default: blur change;    (string)
    valid: function() {                     // default: undefined;      (function)  runs if value is valid
        console.log('Vorname gültig!');
    },
    invalid: function() {                   // default: undefined;      (function)  runs if value is invalid
        console.log('Vorname ungültig!');
    },
    
    // allowed: ['Max'],                    // default: undefined;      (array)     more than one value possible
    prohibited: ['Vorname'],                // default: undefined;      (array)     more than one value possible
    
   
}, function() {                             // default: undefined;      (function)  callback function.
    // do something after initialization of this element.
});


/* PASSWORD - EXAMPLE ---------------------------------------------------- */
$('#password').walidate('validate', {
    eventHandler: 'blur keyup',
    valid: function() {
        console.log('Passwort gültig!');
    },
    invalid: function() {
        console.log('Passwort ungültig!');
    },
   
});


});//]]>  

