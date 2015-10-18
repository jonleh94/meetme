
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

/* TEXT - EXAMPLE ---------------------------------------------------- */
$('#name').walidate('validate', {
	expression: /^[a-zA-Z]{3,}$/, 
    prohibited: ['Nachname'],
    eventHandler: 'blur keyup',
    valid: function() {
        console.log('Nachname gültig!');
    },
    invalid: function() {
        console.log('Nachname ungültig!');
    }
});
$('#vorname').walidate('validate', {
   expression: /^[a-zA-Z]{3,}$/, 
    prohibited: ['Vorname'],
    eventHandler: 'blur keyup',
    valid: function() {
        console.log('Nachname gültig!');
    },
    invalid: function() {
        console.log('Nachname ungültig!');
    }
});

$('#location').walidate('validate', {
   expression: /^[a-zA-Z]{3,}$/, 
    prohibited: ['Vorname'],
    eventHandler: 'blur keyup',
    valid: function() {
        console.log('Nachname gültig!');
    },
    invalid: function() {
        console.log('Nachname ungültig!');
    }
});

/* E-MAIL - EXAMPLE ---------------------------------------------------- */
$('#email').walidate('validate', {
    eventHandler: 'blur keyup',
    expression: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,10})+$/,
    valid: function() {
        console.log('Mail valid!');
    },
    invalid: function() {
        console.log('Mail not valid!');
    },
   
});

/* PHONE - EXAMPLE ---------------------------------------------------- */


/* PASSWORD - EXAMPLE ---------------------------------------------------- */
$('#password').walidate('validate', {
    eventHandler: 'blur keyup',
	expression: /^[a-zA-Z0-9_\.\-\$\&\?]{4,}$/, 
    valid: function() {
        console.log('Passwort gültig!');
    },
    invalid: function() {
        console.log('Passwort ungültig!');
    },
    verify: {
        selector: '#password-verify',
        valid: function() {
            console.log('Bestätigtes Passwort gültig!');
        },
        invalid: function() {
            console.log('Bestätigtes Passwort ungültig!');
        }
    }
});


/*******************************************************************************
 * 
 *    CHECKBOX-ELEMENTS
 *    
 *    Set checkbox as required: $('#checkbox').walidate('validate');
 *    Für: [type="checkbox"]
 * 
 *******************************************************************************/
/* CHECKBOX - EXAMPLE with every possible property ---------------------------------------------------- */
$('#erste-option').walidate('validate', {
    standard: true,                         // default: undefined;      (boolean)   checked if true
    required: false,                        // default: true;           (boolean)   runs if value is invalid
    valid: function() {                     // default: undefined;      (function)  runs if value is valid
        console.log('checked!');
    },
    invalid: function() {                   // default: undefined;      (function)  runs if value is valid
        console.log('unchecked!');
    }
}, function() {                             // default: undefined;      (function)  callback function.
    // do something after initialization of this element.
});
$('#zweite-option').walidate('validate');


/*******************************************************************************
 * 
 *    RADIO-ELEMENTS
 *    
 *    Set radio-group as required: $('input[type="radio"][name="myGroup"]').walidate('validate');
 * 
 *******************************************************************************/
/* RADIO - EXAMPLE with every possible property ---------------------------------------------------- */
$('input[type="radio"][name="radio"]').walidate('validate', {
    standard: ['radio-eins'],               // default: undefined;      (string)    use the text from value-attribute 
    required: false,                        // default: true;           (boolean)
    eventHandler: 'blur change',            // default: blur change;    (string)
    valid: function() {                     // default: undefined;      (function)  runs if value is valid
        console.log('Option gültig');
    },
    invalid: function() {                   // default: undefined;      (function)  runs if value is invalid
        console.log('Option ungültig');
    },
    // allowed: ['radio-eins'],             // default: undefined;      (array)     more than one value possible
    prohibited: ['radio-zwei']              // default: undefined;      (array)     more than one value possible
}, function(){                              // default: undefined;      (function)  callback function.
    // do something after initialization of this element.
});
$('input[type="radio"][name="Optionsschaltergruppe1"]').walidate('validate', {
    standard: ['optionsschalter_eins']
});


/*******************************************************************************
 * 
 *    SELECT-ELEMENTS
 *    
 *    Set a list as required: $('#listen-menu-eins').walidate('validate');
 * 
 *******************************************************************************/
/* SELECTION - EXAMPLE with every possible property ---------------------------------------------------- */
$('#listen-menu-eins').walidate('validate', {
    standard: ['zwei'],                  // default: undefined;      (string)    more than one value possible if its a multiple selection element.
    // required: true,                      // default: true;           (boolean)
    eventHandler: 'blur change',            // default: blur change;    (string)
    valid: function() {                     // default: undefined;      (function)  runs if value is valid
        console.log('Menü 1 valid!');
    },
    invalid: function() {                   // default: undefined;      (function)  runs if value is invalid
        console.log('Menü 1 invalid!');
    },
    // allowed: ['eins'],                   // default: undefined;      (array)     more than one value possible
    prohibited: ['zwei']                    // default: undefined;      (array)     more than one value possible, use the text from value-attribute 
}, function() {                             // default: undefined;      (function)  callback function.
    // do something after initialization of this element.
});

/* MULTIPLE-SELECTION - EXAMPLE ---------------------------------------------------- */
$('#listen-menu-zwei').walidate('validate', {
    allowed: ['zwei'],
    standard: ['zwei', 'vier'],
    valid: function() {
        console.log('Listen Menu zwei gültig');
    },
    invalid: function() {
        console.log('Listen Menu zwei ungültig');
    }
});

/* FILE - EXAMPLE ---------------------------------------------------- */
$('#upload').walidate('validate');

$('#upload-zwei').walidate('validate', {
    expression: /(?:gif|jpg|png|bmp|txt)$/,
    valid: function() {
        console.log('Upload 2 valid!');
    },
    invalid: function() {
        console.log('Upload 2 invalid!');
    }
}, function() {
    $(this).addClass('yeey');
});

/* HTML5 - EXAMPLE ---------------------------------------------------- */
$('#arrival').walidate('validate');
$('#anzahl').walidate('validate');

/* MATH - CALCULATING - EXAMPLE ---------------------------------------------------- */
$('#math').walidate('validate', {
    eventHandler: 'blur keyup',
    allowed: ['4']
});
});//]]>  

