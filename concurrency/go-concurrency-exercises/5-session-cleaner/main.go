//////////////////////////////////////////////////////////////////////
//
// Given is a SessionManager that stores session information in
// memory. The SessionManager itself is working, however, since we
// keep on adding new sessions to the manager our program will
// eventually run out of memory.
//
// Your task is to implement a session cleaner routine that runs
// concurrently in the background and cleans every session that
// hasn't been updated for more than 5 seconds (of course usually
// session times are much longer).
//
// Note that we expect the session to be removed anytime between 5 and
// 7 seconds after the last update. Also, note that you have to be
// very careful in order to prevent race conditions.
//

package main

import (
	"errors"
	"log"
    "sync"
    "time"
)

// SessionManager keeps track of all sessions from creation, updating
// to destroying.
type SessionManager struct {
    L sync.RWMutex
	sessions map[string]Session
    doneCh   chan struct{} // Don't reassign.
}

// Session stores the session's data
type Session struct {
	Data map[string]any
    lastActiveAt time.Time
}

// NewSessionManager creates a new sessionManager
func NewSessionManager() *SessionManager {
    sessions := make(map[string]Session)
	m := &SessionManager{
        sessions: sessions,
        doneCh: make(chan struct{}),
    }
    m.EnableSessionGC(5 * time.Second) // TODO: not the best place to start it, but required by tests.
    return m
}

// EnableSessionGC enables GC background worker that cleans up expired sessions.
func (m *SessionManager) EnableSessionGC(expireAfter time.Duration) func() {
    go func() {
        ticker := time.NewTicker(3 * time.Second)
        defer ticker.Stop()
        for {
            select {
            case <-m.doneCh:
                return
            case <- ticker.C:
                m.doCleanup(expireAfter)
            }
        }
    }()
    return m.DisableSessionGC
}

func (m *SessionManager) DisableSessionGC() {
    defer close(m.doneCh)
}

func (m *SessionManager) doCleanup(expireAfter time.Duration) {
    m.L.RLock()
    expired := make([]string, 0, len(m.sessions))
    for sid, session := range m.sessions {
        if time.Since(session.lastActiveAt) >= expireAfter {
            //log.Printf("marking session with sid = %v as expired\n", sid)
            expired = append(expired, sid)
        }
    }
    m.L.RUnlock()
    for _, sid := range expired {
        m.L.Lock()
        delete(m.sessions, sid)
        m.L.Unlock()
    }
}

// CreateSession creates a new session and returns the sessionID
func (m *SessionManager) CreateSession() (string, error) {
	sessionID, err := MakeSessionID()
	if err != nil {
		return "", err
	}

    m.L.Lock()
	m.sessions[sessionID] = Session{
		Data: make(map[string]any),
        lastActiveAt: time.Now(),
	}
    m.L.Unlock()

	return sessionID, nil
}

// ErrSessionNotFound returned when sessionID not listed in
// SessionManager
var ErrSessionNotFound = errors.New("SessionID does not exists")

// GetSessionData returns data related to session if sessionID is
// found, errors otherwise
func (m *SessionManager) GetSessionData(sessionID string) (map[string]any, error) {
    m.L.RLock()
    defer m.L.RUnlock()
	session, ok := m.sessions[sessionID]
	if !ok {
		return nil, ErrSessionNotFound
	}
	return session.Data, nil
}

// UpdateSessionData overwrites the old session data with the new one
func (m *SessionManager) UpdateSessionData(sessionID string, data map[string]any) error {
    m.L.RLock()
	_, ok := m.sessions[sessionID]
    m.L.RUnlock()
	if !ok {
		return ErrSessionNotFound
	}
    
    // log.Printf("Updating session with sid = %v", sessionID)
	// Hint: you should renew expiry of the session here
    m.L.Lock()
	m.sessions[sessionID] = Session{
		Data: data,
        lastActiveAt: time.Now(),
	}
    m.L.Unlock()

	return nil
}

func main() {
	// Create new sessionManager and new session
	m := NewSessionManager()
    // disableSessionGC := m.EnableSessionGC(5 * time.Second)
    disableSessionGC := m.DisableSessionGC
    defer disableSessionGC()

	sID, err := m.CreateSession()
	if err != nil {
		log.Fatal(err)
	}

	log.Println("Created new session with ID", sID)

	// Update session data
	data := make(map[string]any)
	data["website"] = "longhoang.de"

	err = m.UpdateSessionData(sID, data)
	if err != nil {
		log.Fatal(err)
	}

	log.Println("Update session data, set website to longhoang.de")

	// Retrieve data from manager again
	updatedData, err := m.GetSessionData(sID)
	if err != nil {
		log.Fatal(err)
	}

	log.Println("Get session data:", updatedData)
}

