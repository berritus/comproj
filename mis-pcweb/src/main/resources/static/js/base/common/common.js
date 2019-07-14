/**
 *
 * @param fommId 为表单ID
 */
function getFormData(fommId) {
    var obj = $("#" + fommId).serializeArray();
    var jsonObj = {};

    $.each(obj, function() {
        if (jsonObj[this.name] !== undefined) {
            if (!jsonObj[this.name].push) {
                jsonObj[this.name] = [ jsonObj[this.name] ];
            }
            jsonObj[this.name].push(this.value || '');
        } else {
            jsonObj[this.name] = this.value || '';
        }
    });

    return jsonObj;
}

