"use client"


import { LoginAction } from "./action";


export default function Login() {


  return (
    <>
      <form action={LoginAction}>
        <input placeholder="Email...." type="email" />
        <input placeholder="Password..." type="password" />
        <button type="submit"></button>
      </form>





    </>




  )





}
