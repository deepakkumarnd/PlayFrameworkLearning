console.log("Loaded chat.js")

const chatInput = document.getElementById("chat-input")
const chatContent = document.getElementById("chat-text")

const wsPath = document.getElementById("chat-route").value

const ws = new WebSocket(wsPath)

ws.onopen = (event) => ws.send("New user connected.")

ws.onmessage = (event) => chatContent.value += ("\n" + event.data)

chatInput.onkeydown = (event) => {
  if (event.key === "Enter") {
    ws.send(chatInput.value)
    chatInput.value = ""
   }
}