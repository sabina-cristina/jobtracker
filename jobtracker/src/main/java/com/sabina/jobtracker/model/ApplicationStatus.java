package com.sabina.jobtracker.model;

public enum ApplicationStatus {
    SAVED,      // Job salvat pentru mai târziu, nu ai aplicat încă
    APPLIED,    // Ai trimis CV-ul
    INTERVIEW,  // Ești în faza de interviuri (tehnic, HR, etc.)
    OFFER,      // Felicitări, ai primit o ofertă!
    REJECTED    // Din păcate, ai fost respins
}