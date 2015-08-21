/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).on('keypress', 'form input', function (event)
{
    event.stopImmediatePropagation();

    if (event.which === 13)
    {
        event.preventDefault();
        var $input = $('form input');
        if ($(this).is($input.last()))
        {
            //Time to submit the form!!!!          
        }
        else
        {
            $input.eq($input.index(this) + 1).focus();
        }
    }
});


