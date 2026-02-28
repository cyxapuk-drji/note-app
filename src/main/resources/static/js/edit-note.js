function deleteNote() {
    // Получаем ID заметки из атрибута data
    const noteId = document.querySelector('[th\\:id="${note.id}"]')?.value || 
                   window.location.pathname.split('/').pop();
    
    if (confirm('Удалить заметку?')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/notes/delete/' + noteId;
        document.body.appendChild(form);
        form.submit();
    }
}