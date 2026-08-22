const API_BASE_URL = 'http://localhost:8080'

export type ReviewImage = {
  id: number
  imageUrl: string
  createdAt: string
}

export type Review = {
  id: number
  bookingId: number
  tourId: number
  tourName: string
  userId: number
  reviewerName: string | null
  reviewerAvatar: string | null
  content: string
  rating: number
  images: ReviewImage[]
  createdAt: string
  updatedAt: string
}

export type CreateReviewRequest = {
  bookingId: number
  content: string
  rating: number
  imageUrls?: string[]
}

async function parseError(response: Response): Promise<string> {
  try {
    const body = await response.json() as {
      message?: string
      details?: Record<string, string>
    }

    if (body.message) {
      return body.message
    }

    if (body.details) {
      return Object.values(body.details).join(', ')
    }
  } catch {
    // Ignore malformed/non-JSON error bodies and use the HTTP fallback below.
  }

  return `Request failed with status ${response.status}`
}

export async function getReviews(tourId?: number): Promise<Review[]> {
  const query = tourId == null ? '' : `?tourId=${encodeURIComponent(tourId)}`
  const response = await fetch(`${API_BASE_URL}/api/reviews${query}`, {
    method: 'GET',
    credentials: 'include',
  })

  if (!response.ok) {
    throw new Error(await parseError(response))
  }

  return response.json() as Promise<Review[]>
}

export async function createReview(request: CreateReviewRequest): Promise<Review> {
  const response = await fetch(`${API_BASE_URL}/api/reviews`, {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(request),
  })

  if (!response.ok) {
    throw new Error(await parseError(response))
  }

  return response.json() as Promise<Review>
}
