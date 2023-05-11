import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login/Login";
import NotFound from "./pages/NotFound/NotFound";
import OAuth2Register from "./pages/Register/OAuth2Register";
import OAuth2Merge from "./pages/OAuth2Merge/OAuth2Merge";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" />
        <Route path="/auth/login" element={<Login />} />
        <Route path="/auth/register" />
        <Route path="/auth/oauth2/register" element={<OAuth2Register />} />
        <Route path="/auth/oauth2/merge" element={<OAuth2Merge />} />
        <Route path="/*" element={<NotFound />} />
      </Routes>
    </>
  );
}

export default App;
