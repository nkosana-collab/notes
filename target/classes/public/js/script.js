const search = document.getElementById("input-form");

search.addEventListener("submit", (event) =>{
    event.preventDefault();

    const data = new FormData(event.target);
    const word = data.get("word");

    const options = {
        method: 'GET',
    };

    fetch(`http://localhost:5000/notes/${word}`, options)
        .then(response => response.json())
        .then(data => {
            document.getElementById("note-id").innerText = data.id;
            document.getElementById("note").innerText = data.message;
        });
})



const createForm = document.getElementById("create-form");

createForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const message = formData.get("message");

    fetch("http://localhost:5000/notes", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            message: message
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to create note");
            }
            return response.json();
        })
        .then(note => {
            document.getElementById("note-id").innerText = note.id;
            document.getElementById("note").innerText = note.message;
        })
        .catch(error => {
            console.error("Error creating note:", error);
        });
});