import { RolChat } from "../../enums/RolChat"
import { MessageAndRol } from "./messageAndRol"

export interface ResponseIa {
    messagesAndRol: MessageAndRol[]
    title: string,
    idHistory: string
}

