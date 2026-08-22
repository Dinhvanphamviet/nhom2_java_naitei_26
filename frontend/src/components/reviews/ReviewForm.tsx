import { useState } from 'react'
import type { FormEvent } from 'react'
import { createReview, type Review } from '../../services/reviewService'

type ReviewFormProps = {
  bookingId: number
  onSubmitted?: (review: Review) => void
}

export default function ReviewForm({ bookingId, onSubmitted }: ReviewFormProps) {
  const [content, setContent] = useState('')
  const [rating, setRating] = useState(5)
  const [imageUrlsText, setImageUrlsText] = useState('')
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setSubmitting(true)
    setError(null)
    setSuccess(false)

    const imageUrls = imageUrlsText
      .split('\n')
      .map((url) => url.trim())
      .filter(Boolean)

    try {
      const review = await createReview({
        bookingId,
        content,
        rating,
        imageUrls,
      })

      setContent('')
      setRating(5)
      setImageUrlsText('')
      setSuccess(true)
      onSubmitted?.(review)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unable to submit review')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <h3>Write a review</h3>

      <label>
        Rating
        <select
          value={rating}
          onChange={(event) => setRating(Number(event.target.value))}
          disabled={submitting}
        >
          {[5, 4, 3, 2, 1].map((value) => (
            <option key={value} value={value}>
              {value} star{value > 1 ? 's' : ''}
            </option>
          ))}
        </select>
      </label>

      <label>
        Review
        <textarea
          value={content}
          onChange={(event) => setContent(event.target.value)}
          required
          disabled={submitting}
          rows={5}
        />
      </label>

      <label>
        Image URLs (one per line)
        <textarea
          value={imageUrlsText}
          onChange={(event) => setImageUrlsText(event.target.value)}
          disabled={submitting}
          rows={3}
        />
      </label>

      {error && <p role="alert">{error}</p>}
      {success && <p role="status">Review submitted successfully.</p>}

      <button type="submit" disabled={submitting || content.trim().length === 0}>
        {submitting ? 'Submitting...' : 'Submit review'}
      </button>
    </form>
  )
}
